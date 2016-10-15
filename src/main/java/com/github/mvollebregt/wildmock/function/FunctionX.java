package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionX<R> extends ParameterisedFunction<R> {

    R apply();

    default R apply(Object... params) {
        return apply();
    }
}
