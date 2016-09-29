package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;

import java.util.List;

class CallRecorder {

    private final CallInterceptor defaultInterceptor;
    private CallInterceptor currentInterceptor;

    CallRecorder(CallInterceptor defaultInterceptor) {
        this.defaultInterceptor = defaultInterceptor;
        currentInterceptor = defaultInterceptor;
    }

    CallInterceptor getCurrentInterceptor() {
        return currentInterceptor;
    }

    List<MethodCall> record(ParameterisedAction action, Object[] wildcards, CallInterceptor interceptor) {
        this.currentInterceptor = interceptor;
        action.accept(wildcards);
        this.currentInterceptor = defaultInterceptor;
        return interceptor.getRecordedCalls();
    }
}
