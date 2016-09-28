package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

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

class CallMatcher {

    private final ParameterisedAction action;
    private final ParameterisedFunction behaviour;
    private final CallRecorder callRecorder;
    private final WildcardMarkers wildcardMarkers;
    private final List<MethodCall> remainingCalls;
    private final SimulationValues simulationValues;
    private final Map<MatchingValue, CallMatcher> submatches = new HashMap<>();

    CallMatcher(ParameterisedAction action, ParameterisedFunction behaviour, Class[] wildcardTypes,
                CallRecorder callRecorder) {
        this.action = action;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
        WildcardMatchingCallInterceptor wildcardMatcher = new WildcardMatchingCallInterceptor(wildcardTypes);
        callRecorder.record(action, wildcardMatcher);
        wildcardMatcher.verifyAllWildcardsMatched();
        this.wildcardMarkers = wildcardMatcher.getWildcardMarkers();
        this.remainingCalls = wildcardMatcher.getRecordedCalls();
        this.simulationValues = new SimulationValues(DefaultValueProvider.provideDefault(wildcardTypes), emptyList());
    }

    private CallMatcher(CallMatcher supermatch, SimulationValues simulationValues, List<MethodCall> remainingCalls) {
        this.action = supermatch.action;
        this.behaviour = supermatch.behaviour;
        this.callRecorder = supermatch.callRecorder;
        this.wildcardMarkers = supermatch.wildcardMarkers;
        this.simulationValues = simulationValues;
        this.remainingCalls = remainingCalls;
    }

    Stream<Object[]> match(MethodCall methodCall) {

        Object target = methodCall.getTarget();
        Method method = methodCall.getMethod();
        Object[] arguments = methodCall.getArguments();
        Object returnValue = methodCall.getReturnValue();

        Stream<Object[]> matches = submatches.values().stream().flatMap(submatch -> submatch.match(methodCall));

        if (matchesNextExpectedCall(target, method, arguments)) {
            int methodCallIndex = simulationValues.getReturnValues().size();
            MatchingValue matchingValue = new MatchingValue(method.getReturnType(), returnValue, wildcardMarkers.matchArguments(methodCallIndex, arguments));

            if (remainingCalls.size() == 1) {
                matches = singletonList(simulationValues.extend(matchingValue).getWildcards()).stream();
            } else {
                if (!submatches.containsKey(matchingValue)) {
                    submatches.put(matchingValue, extend(matchingValue));
                }
            }
        }
        return matches;
    }

    Object applyBehaviour(Object[] arguments) {
        return behaviour.apply(arguments);
    }

    boolean matches(List<MethodCall> actualCalls) {
        return actualCalls.stream().reduce(false, (alreadyMatched, methodCall) -> alreadyMatched ||
                match(methodCall).findAny().isPresent(), Boolean::logicalOr);
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

    private CallMatcher extend(MatchingValue matchingValue) {
        SimulationValues newSimulationValues = simulationValues.extend(matchingValue);
        if (matchingValue.containsNewInformation()) {
            List<MethodCall> recordedCalls = callRecorder.record(action, new SimulatingCallInterceptor(newSimulationValues));
            List<MethodCall> newRemainingCalls = recordedCalls.stream().skip(newSimulationValues.getReturnValues().size()).collect(Collectors.toList());
            return new CallMatcher(this, newSimulationValues, newRemainingCalls);
        } else {
            return new CallMatcher(this, newSimulationValues, remainingCalls.stream().skip(1).collect(Collectors.toList()));
        }
    }
}
