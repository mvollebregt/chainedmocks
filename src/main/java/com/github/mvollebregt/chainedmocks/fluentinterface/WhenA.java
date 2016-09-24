package com.github.mvollebregt.chainedmocks.fluentinterface;

import java.util.function.Consumer;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

public class WhenA<A> {

    private final Class[] classes;
    private final Consumer<A> expectedCalls;

    public WhenA(Consumer<A> expectedCalls, Class<A> a) {
        this.expectedCalls = expectedCalls;
        this.classes = new Class[]{a};
    }

    @SuppressWarnings("unchecked")
    public void then(Consumer<A> behaviour) {
        getMockContext().stub(
                params -> expectedCalls.accept((A) params[0]),
                params -> {
                    behaviour.accept((A) params[0]);
                    return null;
                },
                classes);
    }
}
