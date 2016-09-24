package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;

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

    List<MethodCall> record(ParameterisedAction action, Object[] wildcards, ValueProvider valueProvider) {
        this.valueProvider = valueProvider;
        recordedCalls = new ArrayList<>();
        recording = true;
        action.accept(wildcards);
        recording = false;
        return recordedCalls;
    }

    Object registerCall(Object target, Method method, Object[] arguments) {
        Object returnValue = valueProvider.provide(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue));
        return returnValue;
    }
}
