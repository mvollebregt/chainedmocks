package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionX;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class Trigger extends Stub {

    public Trigger(ActionX expectedCalls) {
        this(ParameterisedFunction.from(expectedCalls));
    }

    public void then(ActionX behaviour) {
        then(ParameterisedFunction.from(behaviour));
    }

    Trigger(ParameterisedFunction expectedCalls, Class... classes) {
        super(expectedCalls, classes);
    }

    Trigger(Trigger source, ParameterisedFunction<Boolean> predicate) {
        super(source, predicate);
    }
}
