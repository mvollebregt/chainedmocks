package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class WildcardMatcher {

    private final Wildcard[] wildcards;

    private WildcardMatcher(int numberOfWildcards) {
        this.wildcards = new Wildcard[numberOfWildcards];
    }

    private void setWildcardMarker(int wildcardIndex, int callNumber, int argumentIndex, Object target, Method method) {
        wildcards[wildcardIndex] = new Wildcard(wildcardIndex, callNumber, argumentIndex, target, method);
    }

    List<Wildcard> get(int callNumber, Object target, Method method) {
        return Stream.of(wildcards).filter(wildcard -> wildcard.matches(callNumber, target, method)).collect(Collectors.toList());
    }

    int size() {
        return wildcards.length;
    }

    static WildcardMatcher findWildcards(ParameterisedAction action, Class[] wildcardTypes, CallRecorder callRecorder) {
        ValueProvider valueProvider = new IncrementingValueProvider(1);
        Object[] wildcardValues = new Object[wildcardTypes.length];
        for (int i = 0; i < wildcardTypes.length; i++) {
            wildcardValues[i] = valueProvider.provide(wildcardTypes[i]);
        }
        List<MethodCall> recordedCalls = callRecorder.record(action, wildcardValues, valueProvider);
        WildcardMatcher wildcardMatcher = new WildcardMatcher(wildcardTypes.length);
        for (int callNumber = 0; callNumber < recordedCalls.size(); callNumber++) {
            MethodCall recordedCall = recordedCalls.get(callNumber);
            Object[] arguments = recordedCall.getArguments();
            for (int argumentIndex = 0; argumentIndex < arguments.length; argumentIndex++) {
                for (int wildcardIndex = 0; wildcardIndex < wildcardValues.length; wildcardIndex++) {
                    if (arguments[argumentIndex] == wildcardValues[wildcardIndex]) {
                        wildcardMatcher.setWildcardMarker(wildcardIndex, callNumber, argumentIndex, recordedCall.getTarget(), recordedCall.getMethod());
                    }
                }
            }
        }
        return wildcardMatcher;
    }
}
