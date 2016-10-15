package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionA<A, R> extends Parameterisable<R> {

    R apply(A a);

    @SuppressWarnings("unchecked")
    default ParameterisedFunction<R> parameterised() {
        return params -> apply((A) params[0]);
    }
}
