package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionAB;
import com.github.mvollebregt.wildmock.function.FunctionAB;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class TriggerWithAB<A, B> extends Trigger {

    TriggerWithAB(ActionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        super(ParameterisedAction.from(expectedCalls), a, b);
    }

    TriggerWithAB(TriggerWithAB<A, B> source, FunctionAB<A, B, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }

    public void then(ActionAB<A, B> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
