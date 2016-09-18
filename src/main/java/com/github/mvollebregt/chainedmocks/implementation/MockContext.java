package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.AmbiguousExpectationsException;
import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.List;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final CallSequenceMatcher matcher = new CallSequenceMatcher();

    private MockRecorder recorder;

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void setRecorder(MockRecorder recorder) {
        this.recorder = recorder;
    }

    public void setBehaviour(List<MethodCall> recordedCalls, Action behaviour) {
        matcher.addBehaviour(recordedCalls, behaviour);
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        if (recorder != null) {
            recorder.record(target, method);
        } else {
            List<Action> matches = matcher.match(new MethodCall(target, method));
            if (matches.size() == 1) {
                matches.forEach(Action::execute);
            } else if (matches.size() > 1) {
                throw new AmbiguousExpectationsException();
            }
        }
        return null;
    }
}
