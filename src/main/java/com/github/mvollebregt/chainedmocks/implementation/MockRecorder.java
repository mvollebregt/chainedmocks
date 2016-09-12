package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;

public class MockRecorder {

    private MethodCall recordedCall;

    public MethodCall getRecordedCall() {
        return recordedCall;
    }

    void record(Object target, Method methodCall) {
        this.recordedCall = new MethodCall(target, methodCall);
    }
}
