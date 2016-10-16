package com.github.mvollebregt.wildmock.function;

/**
 * Generic interface for something that can be called, with arbitrary parameters, and that may or may not return a
 * value.
 *
 * @param <R> The type of the returned value, or {@link Void} if the apply function is not meant to return anything.
 */
public interface VarargsCallable<R> {

    /**
     * Executes with the specified arguments.
     *
     * @param arguments a list of arguments.
     * @return a value, or null if the function is not meant to return anything.
     */
    R apply(Object... arguments);

    /**
     * Indicates if the apply function returns a value.
     *
     * @return true if the apply function returns a value, false if it is not meant to return anything.
     */
    boolean returnsValue();

}
