package com.github.mvollebregt.wildmock.implementation.matching;

import com.github.mvollebregt.wildmock.api.MethodCall;
import com.github.mvollebregt.wildmock.implementation.base.CallInterceptor;
import com.github.mvollebregt.wildmock.implementation.base.IncrementingValueProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SimulatingCallInterceptor implements CallInterceptor {

    private int returnValueCount = -1;
    private final List<Object> returnValues;
    private final IncrementingValueProvider valueProvider = new IncrementingValueProvider();
    private final Object[] wildcards;
    private final Map<Object, Integer> wildcardIndices;
    private final WildcardMarkers wildcardMarkers = new WildcardMarkers();
    private final List<MethodCall> recordedCalls = new ArrayList<>();

    SimulatingCallInterceptor(Class[] wildcardTypes, WildcardValues wildcardValues, List<Object> returnValues) {
        this.returnValues = returnValues;
        wildcards = IntStream.range(0, wildcardTypes.length).boxed().map(i -> wildcardValues.get(i).orElse(
                valueProvider.provide(wildcardTypes[i]))).toArray();
        wildcardIndices = IntStream.range(0, wildcardTypes.length).boxed().collect(
                Collectors.toMap(i -> wildcards[i], Function.identity()));
    }

    Object[] getWildcards() {
        return wildcards;
    }

    @Override
    public List<MethodCall> getRecordedCalls() {
        return recordedCalls;
    }

    public Object intercept(Object target, Method method, Object[] arguments) {
        matchMethodArgumentsWithWildcards(arguments);
        Object returnValue = getReturnValue(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue));
        return returnValue;
    }

    WildcardMarkers getWildcardMarkers() {
        return wildcardMarkers;
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

    private Object getReturnValue(Class<?> returnType) {
        returnValueCount++;
        return returnValueCount < returnValues.size() ?
                returnValues.get(returnValueCount) : valueProvider.provide(returnType);
    }

}
