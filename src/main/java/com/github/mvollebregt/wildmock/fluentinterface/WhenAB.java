package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionAB;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenAB<A, B> extends When {

    public WhenAB(FunctionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        super(ParameterisedAction.from(expectedCalls), a, b);
    }

    public void then(FunctionAB<A, B> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
