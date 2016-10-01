package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.Action;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class When extends GenericWhen {

    public When(Action expectedCalls) {
        super(ParameterisedAction.from(expectedCalls));
    }

    public void then(Action behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
