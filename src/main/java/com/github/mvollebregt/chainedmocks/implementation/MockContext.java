package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private Map<Method, Action> behaviour = new HashMap<>();
    private MockRecorder recorder;

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void setRecorder(MockRecorder recorder) {
        this.recorder = recorder;
    }

    public void setBehaviour(Method recordedCalls, Action behaviour) {
        this.behaviour.put(recordedCalls, behaviour);
    }

    public Object intercept(Object target, Method method, Object[] arguments) {
        if (recorder != null) {
            recorder.record(method);
        } else {
            Optional.ofNullable(behaviour.get(method)).ifPresent(Action::execute);
        }
        return null;
    }
}
