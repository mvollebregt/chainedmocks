package com.github.mvollebregt.wildmock.function;

/**
 * Generic interface for an action that can be called, with arbitrary parameters, and that does not return a value.
 */
@FunctionalInterface
interface VarargsAction extends VarargsCallable<Void> {

    void execute(Object... params);

    default Void apply(Object... params) {
        execute(params);
        return null;
    }

    default boolean returnsValue() {
        return false;
    }

}
