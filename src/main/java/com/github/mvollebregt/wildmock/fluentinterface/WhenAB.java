package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionAB;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenAB<A, B, R> extends When<R> {

    public WhenAB(FunctionAB<A, B, R> expectedCalls, Class<A> a, Class<B> b) {
        super(ParameterisedFunction.from(expectedCalls), a, b);
    }

    public WhenAB<A, B, R> with(FunctionAB<A, B, Boolean> predicate) {
        return new WhenAB<>(this, predicate);
    }

    public void then(FunctionAB<A, B, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    private WhenAB(WhenAB<A, B, R> source, FunctionAB<A, B, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }
}
