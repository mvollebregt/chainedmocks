package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.Action;
import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

public class When {

    private final Action expectedCalls;

    public When(Action expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Action behaviour) {
        getMockContext().stub(ParameterisedAction.from(expectedCalls), ParameterisedFunction.from(behaviour));
    }
}
