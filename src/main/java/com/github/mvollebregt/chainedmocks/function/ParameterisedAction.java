package com.github.mvollebregt.chainedmocks.function;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
@SuppressWarnings("unchecked")
public interface ParameterisedAction extends Consumer<Object[]> {

    static ParameterisedAction from(Action expectedCalls) {
        return params -> expectedCalls.execute();
    }

    static ParameterisedAction from(Consumer expectedCalls) {
        return params -> expectedCalls.accept(params[0]);
    }

    static ParameterisedAction from(BiConsumer expectedCalls) {
        return params -> expectedCalls.accept(params[0], params[1]);
    }
    static ParameterisedAction from(Supplier expectedCalls) {
        return params -> expectedCalls.get();
    }

}
