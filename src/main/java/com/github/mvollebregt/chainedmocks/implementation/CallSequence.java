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
        if (isFullyMatched()) {
            // fully matched sequences should be discarded
            return null;
        } else if (methodCalls.get(matchedItems).equals(methodCall)) {
            // a matched method call should increase the matched items by one
            return increased(this);
        } else if (matchedItems != 0) {
            // partially matched call sequences should be kept
            return this;
        } else {
            // sequences with no single match should be discarded
            return null;
        }
    }

    private static CallSequence increased(CallSequence source) {
        CallSequence increase = new CallSequence(source.methodCalls, source.behaviour);
        increase.matchedItems = source.matchedItems + 1;
        return increase;
    }
}
