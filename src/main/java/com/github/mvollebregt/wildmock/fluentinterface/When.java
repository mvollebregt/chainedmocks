package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionX;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

public class When<R> {

    private final ParameterisedAction expectedCalls;
    private final Class[] classes;
    private ParameterisedFunction<Boolean> predicate = arguments -> true;

    public When(FunctionX<R> expectedCalls) {
        this(ParameterisedAction.from(expectedCalls));
    }

    When(ParameterisedAction expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls;
        this.classes = classes;
    }

    When(When source, ParameterisedFunction<Boolean> predicate) {
        this.expectedCalls = source.expectedCalls;
        this.classes = source.classes;
        this.predicate = predicate;
    }

    public void then(FunctionX<R> behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    void setPredicate(ParameterisedFunction<Boolean> predicate) {
        this.predicate = predicate;
    }

    @SuppressWarnings("unchecked")
    void then(ParameterisedFunction behaviour) {
        getMockContext().stub(expectedCalls, predicate, behaviour, classes);
    }

}
