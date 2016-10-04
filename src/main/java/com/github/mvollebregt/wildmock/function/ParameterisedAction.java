package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
@SuppressWarnings("unchecked")
public interface ParameterisedAction {

    void apply(Object[] arguments);

    static ParameterisedAction from(FunctionX expectedCalls) {
        return params -> expectedCalls.apply();
    }

    static ParameterisedAction from(FunctionR expectedCalls) {
        return params -> expectedCalls.apply();
    }

    static ParameterisedAction from(FunctionA expectedCalls) {
        return params -> expectedCalls.apply(params[0]);
    }

    static ParameterisedAction from(FunctionAB expectedCalls) {
        return params -> expectedCalls.apply(params[0], params[1]);
    }

    static ParameterisedAction from(FunctionABC expectedCalls) {
        return params -> expectedCalls.apply(params[0], params[1], params[2]);
    }
}
