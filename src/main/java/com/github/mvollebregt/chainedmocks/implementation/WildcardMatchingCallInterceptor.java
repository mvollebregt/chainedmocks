package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class WildcardMatchingCallInterceptor implements CallInterceptor {

    private final IncrementingValueProvider valueProvider = new IncrementingValueProvider();
    private final Object[] wildcards;
    private final Map<Object, Integer> wildcardIndices;
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
        List<WildcardMarker> wildcardMarkers = matchMethodArgumentsWithWildcards(arguments);
        Object returnValue = valueProvider.provide(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue, wildcardMarkers));
        return returnValue;
    }

    List<Integer> getUnusedWildcardIndices() {
        return wildcardIndices.values().stream().sorted().collect(Collectors.toList());
    }

    private List<WildcardMarker> matchMethodArgumentsWithWildcards(Object[] arguments) {
        return IntStream.range(0, arguments.length).boxed().map(argumentIndex -> {
            Object argument = arguments[argumentIndex];
            Integer wildcardIndex = wildcardIndices.get(argument);
            if (wildcardIndex != null) {
                wildcardIndices.remove(argument);
                return new WildcardMarker(argumentIndex, wildcardIndex);
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
