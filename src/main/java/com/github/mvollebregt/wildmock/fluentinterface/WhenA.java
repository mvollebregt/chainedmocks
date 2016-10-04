package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenA<A> extends When {

    public WhenA(FunctionA<A> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    public void then(FunctionA<A> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
