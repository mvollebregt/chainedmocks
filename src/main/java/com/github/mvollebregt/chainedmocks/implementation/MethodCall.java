package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;

class MethodCall {

    private final Object target;
    private final Method methodCall;

    MethodCall(Object target, Method methodCall) {
        this.target = target;
        this.methodCall = methodCall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodCall that = (MethodCall) o;

        return target.equals(that.target) && methodCall.equals(that.methodCall);

    }

    @Override
    public int hashCode() {
        int result = target.hashCode();
        result = 31 * result + methodCall.hashCode();
        return result;
    }
}
