package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;

public class MockRecorder {

    private Method recordedCall;

    public Method getRecordedCall() {
        return recordedCall;
    }

    public void record(Method methodCall) {
        this.recordedCall = methodCall;
    }
}
