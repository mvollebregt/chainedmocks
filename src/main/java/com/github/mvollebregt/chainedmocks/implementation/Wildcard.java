package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;

class Wildcard {

    private final int wildcardIndex;
    private final int callIndex;
    private final int argumentIndex;
    private final Object target;
    private final Method method;

    Wildcard(int wildcardIndex, int callIndex, int argumentIndex, Object target, Method method) {
        this.wildcardIndex = wildcardIndex;
        this.callIndex = callIndex;
        this.argumentIndex = argumentIndex;
        this.target = target;
        this.method = method;
    }

    int getArgumentIndex() {
        return argumentIndex;
    }

    boolean matches(int callIndex, Object target, Method method) {
        return this.callIndex == callIndex && this.target.equals(target) && this.method.equals(method);
    }

    int getWildcardIndex() {
        return wildcardIndex;
    }
}
