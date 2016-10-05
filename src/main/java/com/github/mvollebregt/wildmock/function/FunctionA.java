package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionA<A, R> {

    R apply(A a);

}
