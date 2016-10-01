package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.util.function.Consumer;

public class WhenA<A> extends GenericWhen {

    public WhenA(Consumer<A> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    public void then(Consumer<A> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }
}
