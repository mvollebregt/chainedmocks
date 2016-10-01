package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

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
