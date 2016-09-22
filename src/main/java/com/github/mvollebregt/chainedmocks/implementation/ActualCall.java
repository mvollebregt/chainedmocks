package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ActualCall {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final Object returnValue;

    ActualCall(Object target, Method method, Object[] arguments, Object returnValue) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.returnValue = returnValue;
    }

    Object getReturnValue() {
        return returnValue;
    }

    boolean matches(RecordedCall recordedCall) {
        boolean arrayEquals;
        try {
            arrayEquals = Arrays.equals(arguments, recordedCall.getArguments());
        } catch (NullPointerException e) {
            System.out.println(arguments[0]);
            System.out.println("-" + recordedCall.getArguments()[0]);
            throw e;
        }
        return target.equals(recordedCall.getTarget()) &&
                method.equals(recordedCall.getMethod()) &&
                arrayEquals;
    }

    @Override
    public String toString() {
        return String.format("%s.%s(%s)", target, method.getName(),
                Stream.of(arguments).map(Object::toString).collect(Collectors.joining(", ")));
    }
}
