package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionAB<A, B, R> {

    R apply(A a, B b);

}
