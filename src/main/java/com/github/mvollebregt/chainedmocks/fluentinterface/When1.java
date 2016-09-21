package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.implementation.MockContext;

import java.util.function.Supplier;

public class When1<T> {

    private final Supplier<T> expectedCalls;

    public When1(Supplier<T> expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Supplier<T> behaviour) {
        MockContext context = MockContext.getMockContext();
        context.stub(context.record(expectedCalls::get), behaviour);
    }

}
