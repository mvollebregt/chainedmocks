package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionABC;
import com.github.mvollebregt.wildmock.function.FunctionABC;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class TriggerABC<A, B, C> extends Trigger {

    public TriggerABC(ActionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(ParameterisedFunction.from(expectedCalls), a, b, c);
    }

    public TriggerABC<A, B, C> with(FunctionABC<A, B, C, Boolean> predicate) {
        return new TriggerABC<>(this, predicate);
    }

    public void then(ActionABC<A, B, C> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    private TriggerABC(TriggerABC<A, B, C> source, FunctionABC<A, B, C, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }
}
