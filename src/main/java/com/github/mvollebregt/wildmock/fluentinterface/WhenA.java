package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.FunctionAR;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenA<A> extends When {

    public WhenA(FunctionA<A> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    @SuppressWarnings("unchecked")
    public WhenA<A> with(FunctionAR<A, Boolean> predicate) {
        setPredicate(ParameterisedFunction.from(predicate));
        return this;
    }

    public void then(FunctionA<A> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}