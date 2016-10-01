package com.github.mvollebregt.chainedmocks.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
@SuppressWarnings("unchecked")
public interface ParameterisedFunction extends Function<Object[], Object> {

    static ParameterisedFunction from(Action behaviour) {
        return params -> {
            behaviour.execute();
            return null;
        };
    }

    static ParameterisedFunction from(Consumer behaviour) {
        return params -> {
            behaviour.accept(params[0]);
            return null;
        };
    }

    static ParameterisedFunction from(BiConsumer behaviour) {
        return params -> {
            behaviour.accept(params[0], params[1]);
            return null;
        };
    }

    static ParameterisedFunction from(Supplier behaviour) {
        return params -> behaviour.get();
    }
}
