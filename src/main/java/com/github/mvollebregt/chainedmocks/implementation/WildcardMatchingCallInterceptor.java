package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.UnusedWildcardException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class WildcardMatchingCallInterceptor implements CallInterceptor {

    private final IncrementingValueProvider valueProvider = new IncrementingValueProvider();
    private final Object[] wildcards;
    private final Map<Object, Integer> wildcardIndices;
    private final WildcardMarkers wildcardMarkers = new WildcardMarkers();
    private final List<MethodCall> recordedCalls = new ArrayList<>();

    WildcardMatchingCallInterceptor(Class[] wildcardTypes) {
        wildcards = valueProvider.provide(wildcardTypes);
        wildcardIndices = IntStream.range(0, wildcards.length).boxed().collect(
                Collectors.toMap(i -> wildcards[i], Function.identity()));
    }

    @Override
    public Object[] getWildcards() {
        return wildcards;
    }

    @Override
    public List<MethodCall> getRecordedCalls() {
        return recordedCalls;
    }

    public Object intercept(Object target, Method method, Object[] arguments) {
        matchMethodArgumentsWithWildcards(arguments);
        Object returnValue = valueProvider.provide(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue));
        return returnValue;
    }

    WildcardMarkers getWildcardMarkers() {
        return wildcardMarkers;
    }

    void verifyAllWildcardsMatched() {
        if (!wildcardIndices.isEmpty()) {
            List<Integer> unusedWildcardIndices = wildcardIndices.values().stream().sorted().collect(Collectors.toList());
            throw new UnusedWildcardException(unusedWildcardIndices);
        }
    }

    private void matchMethodArgumentsWithWildcards(Object[] arguments) {
        for (int argumentIndex = 0; argumentIndex < arguments.length; argumentIndex++) {
            Object argument = arguments[argumentIndex];
            Integer wildcardIndex = wildcardIndices.remove(argument);
            if (wildcardIndex != null) {
                wildcardMarkers.put(recordedCalls.size(), argumentIndex, wildcardIndex);
            }
        }
    }
}
