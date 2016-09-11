package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.Action;
import com.github.mvollebregt.chainedmocks.implementation.MockContext;

public class When {

    public When(Action expectedCalls) {
    }

    public void then(Action behaviour) {
        MockContext.getMockContext().setBehaviour(behaviour);
    }
}
