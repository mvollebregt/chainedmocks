package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionAB<A, B> extends VarargsAction {

    void apply(A a, B b);

    @SuppressWarnings("unchecked")
    default void execute(Object... params) {
        apply((A) params[0], (B) params[1]);
    }
}
