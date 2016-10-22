package com.github.mvollebregt.wildmock.implementation.matching;

import com.github.mvollebregt.wildmock.api.MethodCall;
import com.github.mvollebregt.wildmock.implementation.base.CallInterceptor;
import com.github.mvollebregt.wildmock.implementation.base.IncrementingValueProvider;

import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

class SimulatingCallInterceptor implements CallInterceptor {

    private int returnValueCount = -1;
    private final List<Object> returnValues;
    private final IncrementingValueProvider valueProvider = new IncrementingValueProvider();
    private final Object[] wildcards;
    private final Map<Object, Integer> wildcardIndices;
    private final List<MethodCall> recordedCalls = new ArrayList<>();

    SimulatingCallInterceptor(Class[] wildcardTypes, WildcardValues wildcardValues, List<Object> returnValues) {
        this.returnValues = returnValues;
        wildcards = IntStream.range(0, wildcardTypes.length).boxed().map(i -> wildcardValues.get(i).orElse(
                valueProvider.provide(wildcardTypes[i]))).toArray();
        wildcardIndices = IntStream.range(0, wildcardTypes.length).boxed().collect(
                toMap(i -> wildcards[i], identity()));
    }

    Object[] getWildcards() {
        return wildcards;
    }

    @Override
    public List<MethodCall> getRecordedCalls() {
        return recordedCalls;
    }

    public Object intercept(Object target, Method method, Object[] arguments) {
        Object returnValue = getReturnValue(method.getReturnType());
        Map<Integer, Integer> wildcardMarkers = matchMethodArgumentsWithWildcards(arguments);
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue, wildcardMarkers));
        return returnValue;
    }

    private Object getReturnValue(Class<?> returnType) {
        returnValueCount++;
        return returnValueCount < returnValues.size() ?
                returnValues.get(returnValueCount) : valueProvider.provide(returnType);
    }

    private Map<Integer, Integer> matchMethodArgumentsWithWildcards(Object[] arguments) {
        return IntStream.range(0, arguments.length).boxed().map(argumentIndex -> {
            Integer wildcardIndex = wildcardIndices.remove(arguments[argumentIndex]);
            return wildcardIndex == null ? null : new SimpleEntry<>(argumentIndex, wildcardIndex);
        }).filter(Objects::nonNull).collect(toMap(Entry::getKey, Entry::getValue));
    }
}
