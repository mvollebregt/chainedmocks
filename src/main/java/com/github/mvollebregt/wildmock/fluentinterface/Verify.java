package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

class Verify {

    private final Class[] classes;
    private final ParameterisedAction expectedCalls;

    Verify(ParameterisedAction expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls;
        this.classes = classes;
    }

    @SuppressWarnings("unchecked")
    void with(ParameterisedFunction predicate) {
        if (!getMockContext().verify(expectedCalls, predicate, classes)) {
            throw new VerificationException();
        }
    }
}
