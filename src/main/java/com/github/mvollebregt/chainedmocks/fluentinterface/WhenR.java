package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.util.function.Supplier;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

public class WhenR<R> {

    private final Supplier<R> expectedCalls;

    public WhenR(Supplier<R> expectedCalls) {
        this.expectedCalls = expectedCalls;
    }

    public void then(Supplier<R> behaviour) {
        getMockContext().stub(ParameterisedAction.from(expectedCalls), ParameterisedFunction.from(behaviour));
    }
}
