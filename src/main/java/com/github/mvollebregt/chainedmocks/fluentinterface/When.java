package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.Action;
import com.github.mvollebregt.chainedmocks.implementation.MockContext;

public class When {

    private final Action expectedCalls;

    public When(Action expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Action behaviour) {
        MockContext context = MockContext.getMockContext();
        context.stub(expectedCalls, () -> { behaviour.execute(); return null; });
    }
}
