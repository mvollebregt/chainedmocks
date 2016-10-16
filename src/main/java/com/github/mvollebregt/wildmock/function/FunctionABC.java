package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionABC<A, B, C, R> extends VarargsFunction<R> {

    R apply(A a, B b, C c);

    @SuppressWarnings("unchecked")
    default R apply(Object... params) {
        return apply((A) params[0], (B) params[1], (C) params[2]);
    }
}
