package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionA<A> extends VarargsAction {

    void apply(A a);

    @SuppressWarnings("unchecked")
    default void execute(Object... params) {
        apply((A) params[0]);
    }
}
