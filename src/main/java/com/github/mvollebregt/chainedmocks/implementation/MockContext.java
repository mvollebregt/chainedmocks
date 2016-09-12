package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private Map<MethodCall, Action> behaviour = new HashMap<>();
    private MockRecorder recorder;

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void setRecorder(MockRecorder recorder) {
        this.recorder = recorder;
    }

    public void setBehaviour(MethodCall recordedCall, Action behaviour) {
        this.behaviour.put(recordedCall, behaviour);
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        if (recorder != null) {
            recorder.record(target, method);
        } else {
            Optional.ofNullable(behaviour.get(new MethodCall(target, method))).ifPresent(Action::execute);
        }
        return null;
    }
}
