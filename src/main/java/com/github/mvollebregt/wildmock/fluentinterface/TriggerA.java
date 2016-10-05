package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionA;
import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class TriggerA<A> extends Trigger {

    public TriggerA(ActionA<A> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    @SuppressWarnings("unchecked")
    public TriggerA<A> with(FunctionA<A, Boolean> predicate) {
        setPredicate(ParameterisedFunction.from(predicate));
        return this;
    }

    public void then(ActionA<A> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
