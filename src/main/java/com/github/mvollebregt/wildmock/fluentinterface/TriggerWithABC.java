package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionABC;
import com.github.mvollebregt.wildmock.function.FunctionABC;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class TriggerWithABC<A, B, C> extends Trigger {

    TriggerWithABC(ActionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(ParameterisedAction.from(expectedCalls), a, b, c);
    }

    TriggerWithABC(TriggerWithABC<A, B, C> source, FunctionABC<A, B, C, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }

    public void then(ActionABC<A, B, C> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
