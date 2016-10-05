package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionA;
import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class TriggerAWithPredicate<A> extends Trigger {

    TriggerAWithPredicate(ActionA<A> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    TriggerAWithPredicate(TriggerAWithPredicate<A> source, FunctionA<A, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }

    public void then(ActionA<A> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
