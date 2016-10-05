package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionABC;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenABC<A, B, C, R> extends When<R> {

    public WhenABC(FunctionABC<A, B, C, R> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(ParameterisedAction.from(expectedCalls), a, b, c);
    }

    public void then(FunctionABC<A, B, C, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
