package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
interface ParameterisedAction extends ParameterisedFunction<Void> {

    default boolean returnsValue() {
        return false;
    }

}
