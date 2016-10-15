package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionA;
import com.github.mvollebregt.wildmock.function.FunctionA;

public class TriggerA<A> extends Trigger {

    public TriggerA(ActionA<A> expectedCalls, Class<A> a) {
        super(expectedCalls, a);
    }

    public TriggerA<A> with(FunctionA<A, Boolean> predicate) {
        return new TriggerA<>(this, predicate);
    }

    public void then(ActionA<A> behaviour) {
        super.then(behaviour);
    }

    private TriggerA(TriggerA<A> source, FunctionA<A, Boolean> predicate) {
        super(source, predicate);
    }
}
