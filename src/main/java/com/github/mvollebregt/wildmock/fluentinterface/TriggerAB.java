package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionAB;
import com.github.mvollebregt.wildmock.function.FunctionAB;

public class TriggerAB<A, B> extends Trigger {

    public TriggerAB(ActionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        super(expectedCalls, a, b);
    }

    public TriggerAB<A, B> with(FunctionAB<A, B, Boolean> predicate) {
        return new TriggerAB<>(this, predicate);
    }

    public void then(ActionAB<A, B> behaviour) {
        super.then(behaviour);
    }

    private TriggerAB(TriggerAB<A, B> source, FunctionAB<A, B, Boolean> predicate) {
        super(source, predicate);
    }
}
