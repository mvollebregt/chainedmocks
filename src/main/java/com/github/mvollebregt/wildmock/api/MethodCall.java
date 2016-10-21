package com.github.mvollebregt.wildmock.api;

import java.lang.reflect.Method;
import java.util.Arrays;
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

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodCall that = (MethodCall) o;

        if (!target.equals(that.target)) return false;
        if (!method.equals(that.method)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(arguments, that.arguments)) return false;
        return returnValue != null ? returnValue.equals(that.returnValue) : that.returnValue == null;

    }

    @Override
    public int hashCode() {
        int result = target.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + Arrays.hashCode(arguments);
        result = 31 * result + (returnValue != null ? returnValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s.%s(%s)", target, method.getName(),
                Stream.of(arguments).map(String::valueOf).collect(Collectors.joining(", ")));
    }
}
