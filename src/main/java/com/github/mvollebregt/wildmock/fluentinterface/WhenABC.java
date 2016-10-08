package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionABC;

public class WhenABC<A, B, C, R> extends WhenWithABC<A, B, C, R> {

    public WhenABC(FunctionABC<A, B, C, R> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(expectedCalls, a, b, c);
    }

    @SuppressWarnings("unchecked")
    public WhenWithABC<A, B, C, R> with(FunctionABC<A, B, C, Boolean> predicate) {
        return new WhenWithABC<>(this, predicate);
    }
}
