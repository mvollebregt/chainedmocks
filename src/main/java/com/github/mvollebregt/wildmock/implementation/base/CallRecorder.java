package com.github.mvollebregt.wildmock.implementation.base;

import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

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

    public List<MethodCall> record(ParameterisedFunction action, Object[] wildcards, CallInterceptor interceptor) {
        this.currentInterceptor = interceptor;
        action.apply(wildcards);
        this.currentInterceptor = defaultInterceptor;
        return interceptor.getRecordedCalls();
    }
}
