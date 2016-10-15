package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionX<R> extends Parameterisable<R> {

    R apply();

    @SuppressWarnings("unchecked")
    default ParameterisedFunction<R> parameterised() {
        return params -> apply();
    }
}
