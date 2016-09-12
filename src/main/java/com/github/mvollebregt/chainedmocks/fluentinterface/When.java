package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.Action;
import com.github.mvollebregt.chainedmocks.implementation.MockContext;
import com.github.mvollebregt.chainedmocks.implementation.MockRecorder;

public class When {

    private final Action expectedCalls;

    public When(Action expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Action behaviour) {
        MockContext context = MockContext.getMockContext();
        MockRecorder recorder = new MockRecorder();
        context.setRecorder(recorder);
        expectedCalls.execute();
        context.setRecorder(null);
        context.setBehaviour(recorder.getRecordedCall(), behaviour);
    }
}
