package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionABC;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenABC<A, B, C, R> extends When<R> {

    public WhenABC(FunctionABC<A, B, C, R> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(ParameterisedFunction.from(expectedCalls), a, b, c);
    }

    public WhenABC<A, B, C, R> with(FunctionABC<A, B, C, Boolean> predicate) {
        return new WhenABC<>(this, predicate);
    }

    public void then(FunctionABC<A, B, C, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    private WhenABC(WhenABC<A, B, C, R> source, FunctionABC<A, B, C, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }
}
