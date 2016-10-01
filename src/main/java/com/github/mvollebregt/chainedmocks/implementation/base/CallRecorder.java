package com.github.mvollebregt.chainedmocks.implementation.base;

import java.util.List;

public class CallRecorder {

    private final CallInterceptor defaultInterceptor;
    private CallInterceptor currentInterceptor;

    public CallRecorder(CallInterceptor defaultInterceptor) {
        this.defaultInterceptor = defaultInterceptor;
        currentInterceptor = defaultInterceptor;
    }

    public CallInterceptor getCurrentInterceptor() {
        return currentInterceptor;
    }

    public List<MethodCall> record(ParameterisedAction action, Object[] wildcards, CallInterceptor interceptor) {
        this.currentInterceptor = interceptor;
        action.accept(wildcards);
        this.currentInterceptor = defaultInterceptor;
        return interceptor.getRecordedCalls();
    }
}
