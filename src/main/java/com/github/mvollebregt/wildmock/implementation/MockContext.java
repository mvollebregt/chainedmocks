package com.github.mvollebregt.wildmock.implementation;

import com.github.mvollebregt.wildmock.function.ParameterisedFunction;
import com.github.mvollebregt.wildmock.implementation.base.CallInterceptor;
import com.github.mvollebregt.wildmock.implementation.base.CallRecorder;
import com.github.mvollebregt.wildmock.implementation.matching.CallMatcher;

import java.util.List;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final StubbingCallInterceptor actualCallInterceptor = new StubbingCallInterceptor();
    private final CallRecorder callRecorder = new CallRecorder(actualCallInterceptor);

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void stub(ParameterisedFunction action, ParameterisedFunction<Boolean> predicate,
                     ParameterisedFunction behaviour, Class... wildcardTypes) {
        actualCallInterceptor.addStub(action, predicate, behaviour, wildcardTypes, callRecorder);
    }

    public List<Object[]> verify(ParameterisedFunction action, Class... wildcardTypes) {
        return new CallMatcher(action, null, null, wildcardTypes, callRecorder).matches(actualCallInterceptor.getRecordedCalls());
    }

    CallInterceptor getCurrentInterceptor() {
        return callRecorder.getCurrentInterceptor();
    }
}
