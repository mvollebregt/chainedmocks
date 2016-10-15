package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionAB<A, B, R> extends ParameterisedFunction<R> {

    R apply(A a, B b);

    @SuppressWarnings("unchecked")
    default R apply(Object... params) {
        return apply((A) params[0], (B) params[1]);
    }
}
