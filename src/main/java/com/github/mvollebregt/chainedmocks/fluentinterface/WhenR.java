package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.util.function.Supplier;

public class WhenR<R> extends GenericWhen {

    public WhenR(Supplier<R> expectedCalls) {
        super(ParameterisedAction.from(expectedCalls));
    }

    public void then(Supplier<R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
