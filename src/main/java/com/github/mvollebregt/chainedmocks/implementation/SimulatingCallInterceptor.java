package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class SimulatingCallInterceptor implements CallInterceptor {

    private final SimulationValues simulationValues;
    private int returnValueCount = -1;
    private final List<MethodCall> recordedCalls = new ArrayList<>();

    SimulatingCallInterceptor(SimulationValues simulationValues) {
        this.simulationValues = simulationValues;
    }

    @Override
    public List<MethodCall> getRecordedCalls() {
        return recordedCalls;
    }

    @Override
    public Object[] getWildcards() {
        return simulationValues.getWildcards();
    }

    @Override
    public Object intercept(Object target, Method method, Object[] arguments) {
        Object returnValue = getReturnValue(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue));
        return returnValue;
    }

    private Object getReturnValue(Class<?> returnType) {
        returnValueCount++;
        return returnValueCount < simulationValues.getReturnValues().size() ?
                simulationValues.getReturnValues().get(returnValueCount) : DefaultValueProvider.provideDefault(returnType);
    }

}
