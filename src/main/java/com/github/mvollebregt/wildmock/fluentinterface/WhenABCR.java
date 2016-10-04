package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionABCR;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenABCR<A, B, C, R> extends When {

    public WhenABCR(FunctionABCR<A, B, C, R> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(ParameterisedAction.from(expectedCalls), a, b, c);
    }

    public void then(FunctionABCR<A, B, C, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
