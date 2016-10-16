package com.github.mvollebregt.wildmock.function;

/**
 * Generic interface for a function that can be called, with arbitrary parameters, and that returns a value.
 *
 * @param <R> The type of the returned value.
 */
@FunctionalInterface
public interface VarargsFunction<R> extends VarargsCallable<R> {

    default boolean returnsValue() {
        return true;
    }

}
