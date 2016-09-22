package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class CallRecorder {

    private boolean recording = false;
    private List<MethodCall> recordedCalls;
    private ValueProvider valueProvider;

    boolean isRecording() {
        return recording;
    }

    List<MethodCall> record(Action action, List<Object> returnValues) {
        this.valueProvider = new PrerecordedValueProvider(returnValues);
        recordedCalls = new ArrayList<>();
        recording = true;
        action.execute();
        recording = false;
        return recordedCalls;
    }

    Object registerCall(Object target, Method method, Object[] arguments) {
        Object returnValue = valueProvider.provide(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue));
        return returnValue;
    }
}
