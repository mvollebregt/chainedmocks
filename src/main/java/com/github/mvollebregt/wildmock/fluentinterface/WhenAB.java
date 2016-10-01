package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import java.util.function.BiConsumer;

public class WhenAB<A, B> extends GenericWhen {

    public WhenAB(BiConsumer<A, B> expectedCalls, Class<A> a, Class<B> b) {
        super(ParameterisedAction.from(expectedCalls), a, b);
    }

    public void then(BiConsumer<A, B> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
