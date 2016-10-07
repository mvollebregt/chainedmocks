package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionA;

public class WhenA<A, R> extends WhenWithA<A, R> {

    public WhenA(FunctionA<A, R> expectedCalls, Class<A> a) {
        super(expectedCalls, a);
    }

    @SuppressWarnings("unchecked")
    public WhenWithA<A, R> with(FunctionA<A, Boolean> predicate) {
        return new WhenWithA<>(this, predicate);
    }
}
