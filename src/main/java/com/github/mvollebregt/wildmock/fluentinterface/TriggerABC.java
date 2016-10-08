package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionABC;
import com.github.mvollebregt.wildmock.function.FunctionABC;

public class TriggerABC<A, B, C> extends TriggerWithABC<A, B, C> {

    public TriggerABC(ActionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(expectedCalls, a, b, c);
    }

    @SuppressWarnings("unchecked")
    public TriggerWithABC<A, B, C> with(FunctionABC<A, B, C, Boolean> predicate) {
        return new TriggerWithABC<>(this, predicate);
    }
}
