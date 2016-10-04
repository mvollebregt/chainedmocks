package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionAB<A, B> {

    void apply(A a, B b);

}
