package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionABC<A, B, C> extends Parameterisable<Void> {

    void apply(A a, B b, C c);

    @SuppressWarnings("unchecked")
    default ParameterisedFunction<Void> parameterised() {
        return (ParameterisedAction) params -> {
            apply((A) params[0], (B) params[1], (C) params[2]);
            return null;
        };
    }
}
