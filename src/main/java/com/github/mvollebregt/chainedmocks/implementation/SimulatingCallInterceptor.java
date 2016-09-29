package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class SimulatingCallInterceptor implements CallInterceptor {

    private int returnValueCount = -1;
    private final List<Object> returnValues;
    private final List<MethodCall> recordedCalls = new ArrayList<>();

    SimulatingCallInterceptor(List<Object> returnValues) {
        this.returnValues = returnValues;
    }

    @Override
    public List<MethodCall> getRecordedCalls() {
        return recordedCalls;
    }

    @Override
    public Object intercept(Object target, Method method, Object[] arguments) {
        Object returnValue = getReturnValue(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue));
        return returnValue;
    }

    private Object getReturnValue(Class<?> returnType) {
        returnValueCount++;
        return returnValueCount < returnValues.size() ?
                returnValues.get(returnValueCount) : DefaultValueProvider.provideDefault(returnType);
    }
}
