package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import java.util.function.Supplier;

public class WhenR<R> extends GenericWhen {

    public WhenR(Supplier<R> expectedCalls) {
        super(ParameterisedAction.from(expectedCalls));
    }

    public void then(Supplier<R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
