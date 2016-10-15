package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionAB<A, B> extends ParameterisedAction {

    void apply(A a, B b);

    @SuppressWarnings("unchecked")
    default Void apply(Object... params) {
        apply((A) params[0], (B) params[1]);
        return null;
    }
}
