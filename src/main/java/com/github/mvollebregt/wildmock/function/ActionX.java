package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionX extends Parameterisable<Void> {

    void apply();

    @SuppressWarnings("unchecked")
    default ParameterisedFunction<Void> parameterised() {
        return (ParameterisedAction) params -> {
            apply();
            return null;
        };
    }
}
