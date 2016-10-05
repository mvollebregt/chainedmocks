package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionABC<A, B, C> {

    void apply(A a, B b, C c);

}
