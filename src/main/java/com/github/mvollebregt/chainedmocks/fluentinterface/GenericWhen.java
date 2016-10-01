package com.github.mvollebregt.chainedmocks.fluentinterface;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

class GenericWhen {

    private final Class[] classes;
    private final ParameterisedAction expectedCalls;

    GenericWhen(ParameterisedAction expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls;
        this.classes = classes;
    }

    void then(ParameterisedFunction behaviour) {
        getMockContext().stub(expectedCalls, behaviour, classes);
    }
}
