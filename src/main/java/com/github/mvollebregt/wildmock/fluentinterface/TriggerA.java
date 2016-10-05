package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionA;
import com.github.mvollebregt.wildmock.function.FunctionA;

public class TriggerA<A> extends TriggerAWithPredicate<A> {

    public TriggerA(ActionA<A> expectedCalls, Class<A> a) {
        super(expectedCalls, a);
    }

    @SuppressWarnings("unchecked")
    public TriggerAWithPredicate<A> with(FunctionA<A, Boolean> predicate) {
        return new TriggerAWithPredicate<>(this, predicate);
    }
}
