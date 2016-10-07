package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionAB;
import com.github.mvollebregt.wildmock.function.FunctionAB;

public class TriggerAB<A, B> extends TriggerWithAB<A, B> {

    public TriggerAB(ActionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        super(expectedCalls, a, b);
    }

    @SuppressWarnings("unchecked")
    public TriggerWithAB<A, B> with(FunctionAB<A, B, Boolean> predicate) {
        return new TriggerWithAB<>(this, predicate);
    }

}
