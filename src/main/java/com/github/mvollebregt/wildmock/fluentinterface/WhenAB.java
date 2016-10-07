package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionAB;

public class WhenAB<A, B, R> extends WhenWithAB<A, B, R> {

    public WhenAB(FunctionAB<A, B, R> expectedCalls, Class<A> a, Class<B> b) {
        super(expectedCalls, a, b);
    }

    @SuppressWarnings("unchecked")
    public WhenWithAB<A, B, R> with(FunctionAB<A, B, Boolean> predicate) {
        return new WhenWithAB<>(this, predicate);
    }
}
