package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionABC;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class TriggerABC<A, B, C> extends Trigger {

    public TriggerABC(ActionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(ParameterisedAction.from(expectedCalls), a, b, c);
    }

    public void then(ActionABC<A, B, C> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

}
