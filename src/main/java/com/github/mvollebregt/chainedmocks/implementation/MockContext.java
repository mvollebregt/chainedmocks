package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.implementation.base.CallInterceptor;
import com.github.mvollebregt.chainedmocks.implementation.base.CallRecorder;
import com.github.mvollebregt.chainedmocks.implementation.base.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.implementation.base.ParameterisedFunction;
import com.github.mvollebregt.chainedmocks.implementation.matching.CallMatcher;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final StubbingCallInterceptor actualCallInterceptor = new StubbingCallInterceptor();
    private final CallRecorder callRecorder = new CallRecorder(actualCallInterceptor);

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void stub(ParameterisedAction action, ParameterisedFunction behaviour, Class... wildcardTypes) {
        actualCallInterceptor.addStub(action, behaviour, wildcardTypes, callRecorder);
    }

    public boolean verify(ParameterisedAction action, Class... wildcardTypes) {
        return new CallMatcher(action, null, wildcardTypes, callRecorder).matches(actualCallInterceptor.getRecordedCalls());
    }

    CallInterceptor getCurrentInterceptor() {
        return callRecorder.getCurrentInterceptor();
    }
}
