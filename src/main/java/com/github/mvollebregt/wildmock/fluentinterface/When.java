package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionX;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class When<R> extends Stub {

    public When(FunctionX<R> expectedCalls) {
        this(ParameterisedAction.from(expectedCalls));
    }

    public void then(FunctionX<R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    When(ParameterisedAction expectedCalls, Class... classes) {
        super(expectedCalls, classes);
    }

    When(When<R> source, ParameterisedFunction<Boolean> predicate) {
        super(source, predicate);
    }
}
