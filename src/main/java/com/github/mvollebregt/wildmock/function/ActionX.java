package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionX extends VarargsAction {

    void apply();

    default void execute(Object... params) {
        apply();
    }
}
