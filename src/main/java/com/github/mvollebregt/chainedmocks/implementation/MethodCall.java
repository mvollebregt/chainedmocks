package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MethodCall {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final Object returnValue;
    private final List<WildcardMarker> wildcardMarkers;

    MethodCall(Object target, Method method, Object[] arguments, Object returnValue) {
        this(target, method, arguments, returnValue, new ArrayList<>());
    }

    MethodCall(Object target, Method method, Object[] arguments, Object returnValue, List<WildcardMarker> wildcardMarkers) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.returnValue = returnValue;
        this.wildcardMarkers = wildcardMarkers;
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

    List<WildcardMarker> getWildcardMarkers() {
        return wildcardMarkers;
    }

    boolean matches(Object target, Method method) {
        return this.target.equals(target) &&
                this.method.equals(method);
    }

    boolean matches(Object target, Method method, Object[] arguments) {
        return matches(target, method) &&
                Arrays.equals(this.arguments, arguments);
    }

    @Override
    public String toString() {
        return String.format("%s.%s(%s)", target, method.getName(),
                Stream.of(arguments).map(Object::toString).collect(Collectors.joining(", ")));
    }
}
