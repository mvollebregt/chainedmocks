package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionABC<A, B, C> extends VarargsAction {

    void apply(A a, B b, C c);

    @SuppressWarnings("unchecked")
    default void execute(Object... params) {
        apply((A) params[0], (B) params[1], (C) params[2]);
    }
}
