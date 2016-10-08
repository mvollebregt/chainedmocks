package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

class Stub {

    private final Class[] classes;
    private final ParameterisedAction expectedCalls;
    private ParameterisedFunction<Boolean> predicate;

    Stub(ParameterisedAction expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls;
        this.classes = classes;
    }

    Stub(Stub source, ParameterisedFunction<Boolean> predicate) {
        this.expectedCalls = source.expectedCalls;
        this.classes = source.classes;
        this.predicate = source.predicate == null ? predicate : arguments ->
                source.predicate.apply(arguments) && predicate.apply(arguments);
    }

    void then(ParameterisedFunction behaviour) {
        getMockContext().stub(expectedCalls, predicate, behaviour, classes);
    }
}
