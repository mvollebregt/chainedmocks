package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
@SuppressWarnings("unchecked")
public interface ParameterisedFunction<R> {

    R apply(Object... arguments);

    default boolean returnsValue() {
        return true;
    }

    static ParameterisedFunction<Void> from(ActionX behaviour) {
        return (ParameterisedAction) params -> {
            behaviour.apply();
            return null;
        };
    }

    static <R> ParameterisedFunction<R> from(FunctionX<R> behaviour) {
        return params -> behaviour.apply();
    }

    static ParameterisedFunction<Void> from(ActionA behaviour) {
        return (ParameterisedAction) params -> {
            behaviour.apply(params[0]);
            return null;
        };
    }

    static <A, R> ParameterisedFunction<R> from(FunctionA<A, R> behaviour) {
        return params -> behaviour.apply((A) params[0]);
    }

    static ParameterisedFunction<Void> from(ActionAB behaviour) {
        return (ParameterisedAction) params -> {
            behaviour.apply(params[0], params[1]);
            return null;
        };
    }

    static <A, B, R> ParameterisedFunction<R> from(FunctionAB<A, B, R> behaviour) {
        return params -> behaviour.apply((A) params[0], (B) params[1]);
    }

    static ParameterisedFunction<Void> from(ActionABC behaviour) {
        return (ParameterisedAction) params -> {
            behaviour.apply(params[0], params[1], params[2]);
            return null;
        };
    }

    static <A, B, C, R> ParameterisedFunction<R> from(FunctionABC<A, B, C, R> behaviour) {
        return params -> behaviour.apply((A) params[0], (B) params[1], (C) params[2]);
    }
}
