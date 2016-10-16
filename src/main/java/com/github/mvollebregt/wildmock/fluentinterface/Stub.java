package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.VarargsCallable;
import com.github.mvollebregt.wildmock.function.VarargsFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

class Stub {

    private final Class[] classes;
    private final VarargsCallable expectedCalls;
    private VarargsFunction<Boolean> predicate;

    Stub(VarargsCallable expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls;
        this.classes = classes;
    }

    Stub(Stub source, VarargsFunction<Boolean> predicate) {
        this.expectedCalls = source.expectedCalls;
        this.classes = source.classes;
        this.predicate = source.predicate == null ? predicate : arguments ->
                source.predicate.apply(arguments) && predicate.apply(arguments);
    }

    void then(VarargsCallable behaviour) {
        getMockContext().stub(expectedCalls, predicate, behaviour, classes);
    }
}
