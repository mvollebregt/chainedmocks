package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionAB;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class TriggerAB<A, B> extends Trigger {

    public TriggerAB(ActionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        super(ParameterisedAction.from(expectedCalls), a, b);
    }

    public void then(ActionAB<A, B> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
