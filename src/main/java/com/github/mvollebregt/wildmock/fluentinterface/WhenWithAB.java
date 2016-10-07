package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionAB;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenWithAB<A, B, R> extends When<R> {

    WhenWithAB(FunctionAB<A, B, R> expectedCalls, Class<A> a, Class<B> b) {
        super(ParameterisedAction.from(expectedCalls), a, b);
    }

    WhenWithAB(WhenWithAB<A, B, R> source, FunctionAB<A, B, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }

    public void then(FunctionAB<A, B, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
