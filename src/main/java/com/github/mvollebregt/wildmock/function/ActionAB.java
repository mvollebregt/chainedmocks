package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionAB<A, B> extends Parameterisable<Void> {

    void apply(A a, B b);

    @SuppressWarnings("unchecked")
    default ParameterisedFunction<Void> parameterised() {
        return (ParameterisedAction) params -> {
            apply((A) params[0], (B) params[1]);
            return null;
        };
    }
}
