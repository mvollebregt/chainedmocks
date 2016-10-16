package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionX;
import com.github.mvollebregt.wildmock.function.VarargsCallable;
import com.github.mvollebregt.wildmock.function.VarargsFunction;

public class When<R> extends Stub {

    public When(FunctionX<R> expectedCalls) {
        super(expectedCalls);
    }

    public void then(FunctionX<R> behaviour) {
        super.then(behaviour);
    }

    When(VarargsCallable<R> expectedCalls, Class... classes) {
        super(expectedCalls, classes);
    }

    When(When<R> source, VarargsFunction<Boolean> predicate) {
        super(source, predicate);
    }
}
