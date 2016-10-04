package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionABR;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenABR<A, B, R> extends When {

    public WhenABR(FunctionABR<A, B, R> expectedCalls, Class<A> a, Class<B> b) {
        super(ParameterisedAction.from(expectedCalls), a, b);
    }

    public void then(FunctionABR<A, B, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
