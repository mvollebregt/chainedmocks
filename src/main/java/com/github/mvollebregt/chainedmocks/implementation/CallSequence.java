package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.util.List;

class CallSequence {

    private final List<MethodCall> methodCalls;
    private final Action behaviour;
    private int matchedItems = 0;

    CallSequence(List<MethodCall> methodCalls, Action behaviour) {
        this.methodCalls = methodCalls;
        this.behaviour = behaviour;
    }

    Action getBehaviour() {
        return behaviour;
    }

    boolean isFullyMatched() {
        return methodCalls.size() == matchedItems;
    }

    CallSequence match(MethodCall methodCall) {
        if (matches(methodCall)) {
            return increased(this);
        } else {
            return methodCalls.size() > 0 ? this : null;
        }
    }

    private boolean matches(MethodCall methodCall) {
        return !isFullyMatched() && methodCalls.get(matchedItems).equals(methodCall);
    }

    private static CallSequence increased(CallSequence source) {
        CallSequence increase = new CallSequence(source.methodCalls, source.behaviour);
        increase.matchedItems = source.matchedItems + 1;
        return increase;
    }
}
