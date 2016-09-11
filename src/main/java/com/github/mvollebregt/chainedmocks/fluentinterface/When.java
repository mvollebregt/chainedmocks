package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.Action;

public class When {


    public When(Action expectedCalls) {
    }

    public void then(Action behaviour) {
        behaviour.execute();
    }
}
