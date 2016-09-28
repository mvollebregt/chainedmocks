package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class PartialMatch {

    private final ParameterisedAction action;
    private final CallRecorder callRecorder;
    private final WildcardMarkers wildcardMarkers;
    private final SimulationValues simulationValues;
    private final List<MethodCall> remainingCalls;
    private final Map<MatchingValue, PartialMatch> submatches;

    PartialMatch(ParameterisedAction action, CallRecorder callRecorder, Object[] initialWildcards,
                 WildcardMarkers wildcardMarkers, List<MethodCall> prerecordedCalls) {
        this(action, callRecorder, wildcardMarkers, new SimulationValues(initialWildcards, emptyList()), prerecordedCalls);
    }

    private PartialMatch(ParameterisedAction action, CallRecorder callRecorder, WildcardMarkers wildcardMarkers,
                         SimulationValues simulationValues, List<MethodCall> remainingCalls) {
        this.action = action;
        this.callRecorder = callRecorder;
        this.wildcardMarkers = wildcardMarkers;
        this.simulationValues = simulationValues;
        this.remainingCalls = remainingCalls;
        this.submatches = new HashMap<>();
    }

    Stream<Object[]> match(MethodCall methodCall) {

        Object target = methodCall.getTarget();
        Method method = methodCall.getMethod();
        Object[] arguments = methodCall.getArguments();
        Object returnValue = methodCall.getReturnValue();

        if (remainingCalls.size() == 1) {
            if (matchesNextExpectedCall(target, method, arguments)) {
                int methodCallIndex = simulationValues.getReturnValues().size();
                MatchingValue matchingValue = new MatchingValue(method.getReturnType(), returnValue, wildcardMarkers.matchArguments(methodCallIndex, arguments));
                return singletonList(simulationValues.extend(matchingValue).getWildcards()).stream();
            }
            return Stream.empty();
        } else {
            Stream<Object[]> matches = submatches.values().stream().flatMap(submatch -> submatch.match(methodCall));
            if (matchesNextExpectedCall(target, method, arguments)) {
                int methodCallIndex = simulationValues.getReturnValues().size();
                MatchingValue matchingValue = new MatchingValue(method.getReturnType(), returnValue, wildcardMarkers.matchArguments(methodCallIndex, arguments));
                if (!submatches.containsKey(matchingValue)) {
                    submatches.put(matchingValue, extend(matchingValue));
                }
            }
            return matches;
        }
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

    private PartialMatch extend(MatchingValue matchingValue) {
        SimulationValues newSimulationValues = simulationValues.extend(matchingValue);
        if (matchingValue.containsNewInformation()) {
            List<MethodCall> recordedCalls = callRecorder.record(action, new SimulatingCallInterceptor(newSimulationValues));
            List<MethodCall> newRemainingCalls = recordedCalls.stream().skip(newSimulationValues.getReturnValues().size()).collect(Collectors.toList());
            return new PartialMatch(action, callRecorder, wildcardMarkers, newSimulationValues, newRemainingCalls);
        } else {
            return new PartialMatch(action, callRecorder, wildcardMarkers, newSimulationValues, remainingCalls.stream().skip(1).collect(Collectors.toList()));
        }
    }
}
