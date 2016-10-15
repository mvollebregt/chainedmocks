package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenA<A, R> extends When<R> {

    public WhenA(FunctionA<A, R> expectedCalls, Class<A> a) {
        super(ParameterisedFunction.from(expectedCalls), a);
    }

    public WhenA<A, R> with(FunctionA<A, Boolean> predicate) {
        return new WhenA<>(this, predicate);
    }

    public void then(FunctionA<A, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    private WhenA(WhenA<A, R> source, FunctionA<A, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }
}
