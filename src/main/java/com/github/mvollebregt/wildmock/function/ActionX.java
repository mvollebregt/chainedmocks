package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionX extends ParameterisedAction {

    void apply();

    default Void apply(Object... params) {
        apply();
        return null;
    }
}
