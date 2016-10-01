package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.util.function.Consumer;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

public class WhenA<A> {

    private final Class[] classes;
    private final Consumer<A> expectedCalls;

    public WhenA(Consumer<A> expectedCalls, Class<A> a) {
        this.expectedCalls = expectedCalls;
        this.classes = new Class[]{a};
    }

    public void then(Consumer<A> behaviour) {
        getMockContext().stub(ParameterisedAction.from(expectedCalls), ParameterisedFunction.from(behaviour), classes);
    }
}
