package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionX;
import com.github.mvollebregt.wildmock.function.Parameterisable;

public class When<R> extends Stub {

    public When(FunctionX<R> expectedCalls) {
        super(expectedCalls);
    }

    public void then(FunctionX<R> behaviour) {
        super.then(behaviour);
    }

    When(Parameterisable<R> expectedCalls, Class... classes) {
        super(expectedCalls, classes);
    }

    When(When<R> source, Parameterisable<Boolean> predicate) {
        super(source, predicate);
    }
}
