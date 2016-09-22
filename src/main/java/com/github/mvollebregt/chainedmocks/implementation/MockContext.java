package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final CallRecorder callRecorder = new CallRecorder();
    private final CallStubber callStubber = new CallStubber();
    private final List<ActualCall> actualCalls = new ArrayList<>();


    public static MockContext getMockContext() {
        return mockContext;
    }

    public void stub(Action action, Supplier behaviour) {
        callStubber.addStub(action, behaviour, callRecorder);
    }

    public boolean verify(Action action) {
        return new CallSequenceMatcher(action, null, callRecorder).matches(actualCalls);
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        if (callRecorder.isRecording()) {
            return callRecorder.registerCall(target, method, arguments);
        } else {
            Object returnValue =  callStubber.intercept(target, method, arguments);
            actualCalls.add(new ActualCall(target, method, arguments, returnValue));
            return returnValue;
        }
    }
}
