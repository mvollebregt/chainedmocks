package com.github.mvollebregt.chainedmocks.fluentinterface;

import java.util.function.Supplier;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

public class When1<T> {

    private final Supplier<T> expectedCalls;

    public When1(Supplier<T> expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Supplier<T> behaviour) {
        getMockContext().stub(expectedCalls::get, behaviour);
    }

}
