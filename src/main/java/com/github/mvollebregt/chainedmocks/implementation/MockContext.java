package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.AmbiguousExpectationsException;
import com.github.mvollebregt.chainedmocks.VerificationException;
import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.List;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final CallSequenceMatcher matcher = new CallSequenceMatcher();

    private final MockRecorder actualCallRecorder = new MockRecorder();

    private MockRecorder recorder;

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void setRecorder(MockRecorder recorder) {
        this.recorder = recorder != null ? recorder : actualCallRecorder;
    }

    public void setBehaviour(List<MethodCall> recordedCalls, Action behaviour) {
        matcher.addBehaviour(recordedCalls, behaviour);
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        recorder.record(target, method);
        if (recorder == actualCallRecorder) {
            List<Action> matches = matcher.match(new MethodCall(target, method));
            if (matches.size() == 1) {
                matches.forEach(Action::execute);
            } else if (matches.size() > 1) {
                throw new AmbiguousExpectationsException();
            }
        }
        return null;
    }

    public void verify(List<MethodCall> expectedCalls) {
        CallSequence callSequence = new CallSequence(expectedCalls, null);
        boolean matched = actualCallRecorder.getRecordedCalls().stream().reduce(false, (acc, call) -> {
            callSequence.match(call);
            return acc || callSequence.isFullyMatched();
        }, Boolean::logicalOr);
        if (!matched) {
            throw new VerificationException();
        }
    }
}
