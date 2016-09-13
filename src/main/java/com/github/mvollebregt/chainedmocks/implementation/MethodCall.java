package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;

class MethodCall {

    private final Object target;
    private final Method method;

    MethodCall(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodCall that = (MethodCall) o;

        return target.equals(that.target) && method.equals(that.method);

    }

    @Override
    public int hashCode() {
        int result = target.hashCode();
        result = 31 * result + method.hashCode();
        return result;
    }
}
