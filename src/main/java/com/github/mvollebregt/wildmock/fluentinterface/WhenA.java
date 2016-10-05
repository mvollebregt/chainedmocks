package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenA<A, R> extends When<R> {

    public WhenA(FunctionA<A, R> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    @SuppressWarnings("unchecked")
    public WhenA<A, R> with(FunctionA<A, Boolean> predicate) {
        setPredicate(ParameterisedFunction.from(predicate));
        return this;
    }

    public void then(FunctionA<A, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
