package com.github.mvollebregt.wildmock.function;

@FunctionalInterface
@SuppressWarnings("unchecked")
public interface ParameterisedFunction<R> {

    R apply(Object[] arguments);

    static ParameterisedFunction from(FunctionX behaviour) {
        return params -> {
            behaviour.apply();
            return null;
        };
    }

    static ParameterisedFunction from(FunctionR behaviour) {
        return params -> behaviour.apply();
    }

    static ParameterisedFunction from(FunctionA behaviour) {
        return params -> {
            behaviour.apply(params[0]);
            return null;
        };
    }

    static ParameterisedFunction from(FunctionAR behaviour) {
        return params -> behaviour.apply(params[0]);
    }

    static ParameterisedFunction from(FunctionAB behaviour) {
        return params -> {
            behaviour.apply(params[0], params[1]);
            return null;
        };
    }

    static ParameterisedFunction from(FunctionABR behaviour) {
        return params -> behaviour.apply(params[0], params[1]);
    }

    static ParameterisedFunction from(FunctionABC behaviour) {
        return params -> {
            behaviour.apply(params[0], params[1], params[2]);
            return null;
        };
    }

    static ParameterisedFunction from(FunctionABCR behaviour) {
        return params -> behaviour.apply(params[0], params[1], params[2]);
    }
}
