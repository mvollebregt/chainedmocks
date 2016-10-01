package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.Action;
import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

public class When extends GenericWhen {

    public When(Action expectedCalls) {
        super(ParameterisedAction.from(expectedCalls));
    }

    public void then(Action behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
