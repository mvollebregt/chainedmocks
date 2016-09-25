package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class SimulatingCallInterceptor implements CallInterceptor {

    private PartialMatch partialMatch;
    private int returnValueCount = -1;
    private List<MethodCall> recordedCalls = new ArrayList<>();

    SimulatingCallInterceptor(PartialMatch partialMatch) {
        this.partialMatch = partialMatch;
    }

    @Override
    public List<MethodCall> getRecordedCalls() {
        return recordedCalls;
    }

    @Override
    public Object[] getWildcards() {
        return partialMatch.getWildcards();
    }

    @Override
    public Object intercept(Object target, Method method, Object[] arguments) {
        Object returnValue = getReturnValue(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue));
        return returnValue;
    }

    private Object getReturnValue(Class<?> returnType) {
        returnValueCount++;
        return returnValueCount < partialMatch.getReturnValues().size() ?
                partialMatch.getReturnValues().get(returnValueCount) : DefaultValueProvider.provideDefault(returnType);
    }

}
