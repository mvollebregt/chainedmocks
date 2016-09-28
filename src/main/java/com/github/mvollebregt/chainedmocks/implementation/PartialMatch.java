package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

class PartialMatch {

    private final WildcardMarkers wildcardMarkers;
    private final SimulationValues simulationValues;
    private final List<MethodCall> remainingCalls;
    private final Set<MatchingValue> alreadyMatchedValues = new HashSet<>();

    PartialMatch(Object[] initialWildcards, WildcardMarkers wildcardMarkers, List<MethodCall> prerecordedCalls) {
        this(wildcardMarkers, new SimulationValues(initialWildcards, emptyList()), prerecordedCalls);
    }

    private PartialMatch(WildcardMarkers wildcardMarkers, SimulationValues simulationValues, List<MethodCall> remainingCalls) {
        this.wildcardMarkers = wildcardMarkers;
        this.simulationValues = simulationValues;
        this.remainingCalls = remainingCalls;
    }

    Object[] getWildcards() {
        return simulationValues.getWildcards();
    }

    boolean isFullmatch() {
        return remainingCalls.isEmpty();
    }

    PartialMatch newMatch(MethodCall methodCall, ParameterisedAction action, CallRecorder callRecorder) {
        Object target = methodCall.getTarget();
        Method method = methodCall.getMethod();
        Object[] arguments = methodCall.getArguments();
        Object returnValue = methodCall.getReturnValue();
        if (matchesNextExpectedCall(target, method, arguments)) {
            int methodCallIndex = simulationValues.getReturnValues().size();
            MatchingValue matchingValue = new MatchingValue(method.getReturnType(), returnValue, wildcardMarkers.matchArguments(methodCallIndex, arguments));
            if (remainingCalls.size() == 1 || alreadyMatchedValues.add(matchingValue)) {
                return extend(matchingValue, action, callRecorder);
            }
        }
        return null;
    }

    private boolean matchesNextExpectedCall(Object target, Method method, Object[] arguments) {
        int methodCallIndex = simulationValues.getReturnValues().size();
        Set<Integer> argumentIndicesForWildcards = wildcardMarkers.getArgumentIndicesForWildcards(methodCallIndex);
        MethodCall nextExpectedCall = remainingCalls.get(0);
        return target.equals(nextExpectedCall.getTarget()) &&
                method.equals(nextExpectedCall.getMethod()) &&
                IntStream.range(0, arguments.length).
                        filter(argumentIndex -> !argumentIndicesForWildcards.contains(argumentIndex)).
                        allMatch(argumentIndex ->
                                arguments[argumentIndex].equals(nextExpectedCall.getArguments()[argumentIndex]));
    }

    private PartialMatch extend(MatchingValue matchingValue, ParameterisedAction action, CallRecorder callRecorder) {
        SimulationValues newSimulationValues = simulationValues.extend(matchingValue);
        if (matchingValue.containsNewInformation()) {
            List<MethodCall> recordedCalls = callRecorder.record(action, new SimulatingCallInterceptor(newSimulationValues));
            List<MethodCall> newRemainingCalls = recordedCalls.stream().skip(newSimulationValues.getReturnValues().size()).collect(Collectors.toList());
            return new PartialMatch(wildcardMarkers, newSimulationValues, newRemainingCalls);
        } else {
            return new PartialMatch(wildcardMarkers, newSimulationValues, remainingCalls.stream().skip(1).collect(Collectors.toList()));
        }
    }
}
