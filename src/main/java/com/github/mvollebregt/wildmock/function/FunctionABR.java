package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionABR<A, B, R> {

    R apply(A a, B b);

}
