package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionA;
import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class TriggerA<A> extends Trigger {

    public TriggerA(ActionA<A> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    public TriggerA<A> with(FunctionA<A, Boolean> predicate) {
        return new TriggerA<>(this, predicate);
    }

    public void then(ActionA<A> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    private TriggerA(TriggerA<A> source, FunctionA<A, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }
}
