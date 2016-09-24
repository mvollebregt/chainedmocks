package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final CallRecorder callRecorder = new CallRecorder();
    private final CallStubber callStubber = new CallStubber();
    private final List<MethodCall> actualCalls = new ArrayList<>();


    public static MockContext getMockContext() {
        return mockContext;
    }

    public void stub(ParameterisedAction action, ParameterisedFunction behaviour, Class... wildcardTypes) {
        callStubber.addStub(action, behaviour, wildcardTypes, callRecorder);
    }

    public boolean verify(ParameterisedAction action, Class... wildcardTypes) {
        return new CallSequenceMatcher(action, null, wildcardTypes, callRecorder).matches(actualCalls);
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        if (callRecorder.isRecording()) {
            return callRecorder.registerCall(target, method, arguments);
        } else {
            Object returnValue =  callStubber.intercept(target, method, arguments);
            actualCalls.add(new MethodCall(target, method, arguments, returnValue));
            return returnValue;
        }
    }
}
