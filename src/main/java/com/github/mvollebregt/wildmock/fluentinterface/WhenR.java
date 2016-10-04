package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionR;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class WhenR<R> extends When {

    public WhenR(FunctionR<R> expectedCalls) {
        super(ParameterisedAction.from(expectedCalls));
    }

    public void then(FunctionR<R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
