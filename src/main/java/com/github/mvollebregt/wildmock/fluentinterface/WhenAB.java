package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionAB;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenAB<A, B, R> extends When<R> {

    public WhenAB(FunctionAB<A, B, R> expectedCalls, Class<A> a, Class<B> b) {
        super(ParameterisedAction.from(expectedCalls), a, b);
    }

    public void then(FunctionAB<A, B, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
