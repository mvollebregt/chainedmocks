package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.Action;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

public class When {

    private final Action expectedCalls;

    public When(Action expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Action behaviour) {
        getMockContext().stub(
                params -> expectedCalls.execute(),
                params -> {
                    behaviour.execute();
                    return null;
                });
    }
}
