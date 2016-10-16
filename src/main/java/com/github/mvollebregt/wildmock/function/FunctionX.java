package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface FunctionX<R> extends VarargsFunction<R> {

    R apply();

    default R apply(Object... params) {
        return apply();
    }
}
