package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
@SuppressWarnings("unchecked")
public interface ParameterisedFunction<R> {

    R apply(Object[] arguments);

    static ParameterisedFunction from(ActionX behaviour) {
        return params -> {
            behaviour.apply();
            return null;
        };
    }

    static <R> ParameterisedFunction from(FunctionX<R> behaviour) {
        return params -> behaviour.apply();
    }

    static ParameterisedFunction from(ActionA behaviour) {
        return params -> {
            behaviour.apply(params[0]);
            return null;
        };
    }

    static <A, R> ParameterisedFunction<R> from(FunctionA<A, R> behaviour) {
        return params -> behaviour.apply((A) params[0]);
    }

    static ParameterisedFunction from(ActionAB behaviour) {
        return params -> {
            behaviour.apply(params[0], params[1]);
            return null;
        };
    }

    static <A, B, R> ParameterisedFunction<R> from(FunctionAB<A, B, R> behaviour) {
        return params -> behaviour.apply((A) params[0], (B) params[1]);
    }

    static ParameterisedFunction from(ActionABC behaviour) {
        return params -> {
            behaviour.apply(params[0], params[1], params[2]);
            return null;
        };
    }

    static <A, B, C, R> ParameterisedFunction<R> from(FunctionABC<A, B, C, R> behaviour) {
        return params -> behaviour.apply((A) params[0], (B) params[1], (C) params[2]);
    }
}
