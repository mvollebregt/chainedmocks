package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionAB<A, B, R> extends Parameterisable<R> {

    R apply(A a, B b);

    @SuppressWarnings("unchecked")
    default ParameterisedFunction<R> parameterised() {
        return params -> apply((A) params[0], (B) params[1]);
    }
}
