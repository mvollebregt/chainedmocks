package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionABC<A, B, C, R> extends Parameterisable<R> {

    R apply(A a, B b, C c);

    @SuppressWarnings("unchecked")
    default ParameterisedFunction<R> parameterised() {
        return params -> apply((A) params[0], (B) params[1], (C) params[2]);
    }
}
