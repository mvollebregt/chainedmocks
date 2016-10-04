package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionX;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

public class When {

    private final Class[] classes;
    private final ParameterisedAction expectedCalls;
    private ParameterisedFunction predicate = arguments -> true;

    public When(FunctionX expectedCalls) {
        this(ParameterisedAction.from(expectedCalls));
    }

    When(ParameterisedAction expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls;
        this.classes = classes;
    }

    public void then(FunctionX behaviour) {
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
