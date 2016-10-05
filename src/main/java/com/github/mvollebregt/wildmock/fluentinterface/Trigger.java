package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionX;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

public class Trigger {

    private final Class[] classes;
    private final ParameterisedAction expectedCalls;
    private ParameterisedFunction predicate = arguments -> true;

    public Trigger(ActionX expectedCalls) {
        this(ParameterisedAction.from(expectedCalls));
    }

    Trigger(ParameterisedAction expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls;
        this.classes = classes;
    }

    Trigger(Trigger source, ParameterisedFunction<Boolean> predicate) {
        this.expectedCalls = source.expectedCalls;
        this.classes = source.classes;
        this.predicate = predicate;
    }

    public void then(ActionX behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    @SuppressWarnings("unchecked")
    void then(ParameterisedFunction behaviour) {
        getMockContext().stub(expectedCalls, predicate, behaviour, classes);
    }

}
