package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenWithA<A, R> extends When<R> {

    WhenWithA(FunctionA<A, R> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    WhenWithA(WhenWithA<A, R> source, FunctionA<A, Boolean> predicate) {
        super(source, ParameterisedFunction.from(predicate));
    }

    public void then(FunctionA<A, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
