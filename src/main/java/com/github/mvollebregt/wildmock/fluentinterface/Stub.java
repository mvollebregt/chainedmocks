package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.Parameterisable;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

class Stub {

    private final Class[] classes;
    private final ParameterisedFunction expectedCalls;
    private ParameterisedFunction<Boolean> predicate;

    Stub(Parameterisable expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls.parameterised();
        this.classes = classes;
    }

    Stub(Stub source, Parameterisable<Boolean> predicate) {
        this.expectedCalls = source.expectedCalls;
        this.classes = source.classes;
        this.predicate = source.predicate == null ? predicate.parameterised() : arguments ->
                source.predicate.apply(arguments) && predicate.parameterised().apply(arguments);
    }

    void then(Parameterisable behaviour) {
        getMockContext().stub(expectedCalls, predicate, behaviour.parameterised(), classes);
    }
}
