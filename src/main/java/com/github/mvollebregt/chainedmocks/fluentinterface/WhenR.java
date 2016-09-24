package com.github.mvollebregt.chainedmocks.fluentinterface;

import java.util.function.Supplier;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

public class WhenR<R> {

    private final Supplier<R> expectedCalls;

    public WhenR(Supplier<R> expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Supplier<R> behaviour) {
        getMockContext().stub(
                params -> expectedCalls.get(),
                params -> behaviour.get());
    }
}
