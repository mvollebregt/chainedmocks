package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.Action;

public class When {

    private Action expectedCalls;
    private Action behaviour;

    public When(Action expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Action behaviour) {
        this.behaviour = behaviour;
    }
}
