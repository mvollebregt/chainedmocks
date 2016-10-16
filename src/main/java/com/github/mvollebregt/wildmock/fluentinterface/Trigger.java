package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionX;
import com.github.mvollebregt.wildmock.function.VarargsCallable;
import com.github.mvollebregt.wildmock.function.VarargsFunction;

public class Trigger extends Stub {

    public Trigger(ActionX expectedCalls) {
        super(expectedCalls);
    }

    public void then(ActionX behaviour) {
        super.then(behaviour);
    }

    Trigger(VarargsCallable expectedCalls, Class... classes) {
        super(expectedCalls, classes);
    }

    Trigger(Trigger source, VarargsFunction<Boolean> predicate) {
        super(source, predicate);
    }
}
