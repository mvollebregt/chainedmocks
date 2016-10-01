package com.github.mvollebregt.wildmock.implementation.base;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodCall {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final Object returnValue;

    public MethodCall(Object target, Method method, Object[] arguments, Object returnValue) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.returnValue = returnValue;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    @Override
    public String toString() {
        return String.format("%s.%s(%s)", target, method.getName(),
                Stream.of(arguments).map(String::valueOf).collect(Collectors.joining(", ")));
    }
}
