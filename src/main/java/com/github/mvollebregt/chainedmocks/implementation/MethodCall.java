package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MethodCall {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private Object returnValue;

    MethodCall(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    MethodCall withReturnValue(Object returnValue) {
        this.returnValue = returnValue;
        return this;
    }

    Object getReturnValue() {
        return returnValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodCall that = (MethodCall) o;

        if (!target.equals(that.target)) return false;
        if (!method.equals(that.method)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(arguments, that.arguments);

    }

    @Override
    public int hashCode() {
        int result = target.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s.%s(%s)", target, method.getName(),
                Stream.of(arguments).map(Object::toString).collect(Collectors.joining(", ")));
    }
}
