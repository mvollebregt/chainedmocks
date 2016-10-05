package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionABC<A, B, C, R> {

    R apply(A a, B b, C c);

}
