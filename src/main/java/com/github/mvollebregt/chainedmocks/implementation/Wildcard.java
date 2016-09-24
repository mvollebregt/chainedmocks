package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;

class Wildcard {

    private final int wildcardIndex;
    private final int callNumber;
    private final int argumentIndex;
    private final Object target;
    private final Method method;

    Wildcard(int wildcardIndex, int callNumber, int argumentIndex, Object target, Method method) {
        this.wildcardIndex = wildcardIndex;
        this.callNumber = callNumber;
        this.argumentIndex = argumentIndex;
        this.target = target;
        this.method = method;
    }

    int getArgumentNumber() {
        return argumentIndex;
    }

    boolean matches(int callNumber, Object target, Method method) {
        return this.callNumber == callNumber && this.target.equals(target) && this.method.equals(method);
    }

    int getWildcardIndex() {
        return wildcardIndex;
    }
}
