package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionABC<A, B, C> {

    void apply(A a, B b, C c);

}
