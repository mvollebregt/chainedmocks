package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MethodCall {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final Object returnValue;

    MethodCall(Object target, Method method, Object[] arguments, Object returnValue) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.returnValue = returnValue;
    }

    Object getTarget() {
        return target;
    }

    Method getMethod() {
        return method;
    }

    Object[] getArguments() {
        return arguments;
    }

    Object getReturnValue() {
        return returnValue;
    }

    @Override
    public String toString() {
        return String.format("%s.%s(%s)", target, method.getName(),
                Stream.of(arguments).map(String::valueOf).collect(Collectors.joining(", ")));
    }
}
