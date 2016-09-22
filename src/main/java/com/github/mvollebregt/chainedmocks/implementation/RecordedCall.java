package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RecordedCall {

    private final Object target;
    private final Method method;
    private final Object[] arguments;

    RecordedCall(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
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

    @Override
    public String toString() {
        return String.format("%s.%s(%s)", target, method.getName(),
                Stream.of(arguments).map(Object::toString).collect(Collectors.joining(", ")));
    }
}
