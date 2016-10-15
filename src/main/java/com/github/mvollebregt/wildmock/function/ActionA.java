package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionA<A> extends Parameterisable<Void> {

    void apply(A a);

    @SuppressWarnings("unchecked")
    default ParameterisedFunction<Void> parameterised() {
        return (ParameterisedAction) params -> {
            apply((A) params[0]);
            return null;
        };
    }
}
