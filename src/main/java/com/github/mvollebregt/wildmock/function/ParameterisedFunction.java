package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ParameterisedFunction<R> {

    R apply(Object... arguments);

    default boolean returnsValue() {
        return true;
    }

}
