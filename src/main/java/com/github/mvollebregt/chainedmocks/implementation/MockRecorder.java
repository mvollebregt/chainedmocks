package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MockRecorder {

    private List<MethodCall> recordedCalls = new ArrayList<>();

    public List<MethodCall> getRecordedCalls() {
        return recordedCalls;
    }

    void record(Object target, Method methodCall) {
        this.recordedCalls.add(new MethodCall(target, methodCall));
    }
}
