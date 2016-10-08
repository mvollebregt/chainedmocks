package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionABC;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenWithABC<A, B, C, R> extends When<R> {

    WhenWithABC(FunctionABC<A, B, C, R> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(ParameterisedAction.from(expectedCalls), a, b, c);
    }

    WhenWithABC(WhenWithABC<A, B, C, R> source, FunctionABC<A, B, C, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }

    public void then(FunctionABC<A, B, C, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
