package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionAR<A, R> {

    R apply(A a);

}
