package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionABC<A, B, C> extends ParameterisedAction {

    void apply(A a, B b, C c);

    @SuppressWarnings("unchecked")
    default Void apply(Object... params) {
        apply((A) params[0], (B) params[1], (C) params[2]);
        return null;
    }
}
