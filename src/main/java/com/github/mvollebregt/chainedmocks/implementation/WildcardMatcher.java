package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.UnusedWildcardException;
import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class WildcardMatcher {

    private final Class[] wildcardTypes;
    private final Wildcard[] wildcards;

    private WildcardMatcher(Class[] wildcardTypes) {
        this.wildcardTypes = wildcardTypes;
        this.wildcards = new Wildcard[wildcardTypes.length];
    }

    Class[] getTypes() {
        return wildcardTypes;
    }

    List<Wildcard> get(int callIndex, Object target, Method method) {
        return Stream.of(wildcards).filter(wildcard -> wildcard != null && wildcard.matches(callIndex, target, method)).
                collect(Collectors.toList());
    }

    private void setWildcardMarker(int wildcardIndex, int callIndex, int argumentIndex, Object target, Method method) {
        wildcards[wildcardIndex] = new Wildcard(wildcardIndex, callIndex, argumentIndex, target, method);
    }

    static WildcardMatcher findWildcards(ParameterisedAction action, Class[] wildcardTypes, CallRecorder callRecorder) {
        ValueProvider valueProvider = new IncrementingValueProvider();
        Object[] wildcardValues = new Object[wildcardTypes.length];
        for (int i = 0; i < wildcardTypes.length; i++) {
            wildcardValues[i] = valueProvider.provide(wildcardTypes[i]);
        }
        List<MethodCall> recordedCalls = callRecorder.record(action, wildcardValues, valueProvider);
        WildcardMatcher wildcardMatcher = new WildcardMatcher(wildcardTypes);
        for (int callIndex = 0; callIndex < recordedCalls.size(); callIndex++) {
            MethodCall recordedCall = recordedCalls.get(callIndex);
            Object[] arguments = recordedCall.getArguments();
            for (int argumentIndex = 0; argumentIndex < arguments.length; argumentIndex++) {
                for (int wildcardIndex = 0; wildcardIndex < wildcardValues.length; wildcardIndex++) {
                    if (arguments[argumentIndex].equals(wildcardValues[wildcardIndex])) {
                        wildcardMatcher.setWildcardMarker(wildcardIndex, callIndex, argumentIndex, recordedCall.getTarget(), recordedCall.getMethod());
                    }
                }
            }
        }
        for (int i = 0; i < wildcardTypes.length; i++) {
            if (wildcardMatcher.wildcards[i] == null) {
                throw new UnusedWildcardException(wildcardTypes[i], i);
            }
        }
        return wildcardMatcher;
    }
}
