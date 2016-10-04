package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionAR;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenAR<A, R> extends When {

    public WhenAR(FunctionAR<A, R> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    public void then(FunctionAR<A, R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
