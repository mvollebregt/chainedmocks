package com.github.mvollebregt.chainedmocks.implementation;

import java.util.List;

public class CallSequence {

    private final List<MethodCall> methodCalls;

    CallSequence(List<MethodCall> methodCalls) {
        this.methodCalls = methodCalls;
    }

    List<MethodCall> getMethodCalls() {
        return methodCalls;
    }

    public boolean matches(CallSequence actualCalls) {
        CallSequenceMatcher matcher = new CallSequenceMatcher(this);
        return actualCalls.getMethodCalls().stream().reduce(false, (acc, call) -> {
            matcher.match(call);
            return acc || matcher.isFullyMatched();
        }, Boolean::logicalOr);
    }
}
