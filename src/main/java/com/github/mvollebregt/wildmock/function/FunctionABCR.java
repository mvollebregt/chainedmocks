package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionABCR<A, B, C, R> {

    R apply(A a, B b, C c);

}
