package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
public interface ActionA<A> extends ParameterisedAction {

    void apply(A a);

    @SuppressWarnings("unchecked")
    default Void apply(Object... params) {
        apply((A) params[0]);
        return null;
    }
}
