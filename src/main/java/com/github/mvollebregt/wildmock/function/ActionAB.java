package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionAB<A, B> {

    void apply(A a, B b);

}
