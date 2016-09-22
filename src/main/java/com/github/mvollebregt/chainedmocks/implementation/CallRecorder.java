package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class CallRecorder {

    private boolean recording = false;
    private List<RecordedCall> recordedCalls;
    private ValueProvider valueProvider;

    boolean isRecording() {
        return recording;
    }

    List<RecordedCall> record(Action action, List<Object> returnValues) {
        this.valueProvider = new PrerecordedValueProvider(returnValues);
        recordedCalls = new ArrayList<>();
        recording = true;
        action.execute();
        recording = false;
        return recordedCalls;
    }

    Object registerCall(Object target, Method method, Object[] arguments) {
        recordedCalls.add(new RecordedCall(target, method, arguments));
        return valueProvider.provide(method.getReturnType());
    }
}
