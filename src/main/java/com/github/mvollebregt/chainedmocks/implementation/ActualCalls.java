package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.util.List;

public class ActualCalls {

    private final List<MethodCall> actualCalls;

    ActualCalls(List<MethodCall> actualCalls) {
        this.actualCalls = actualCalls;
    }

    public boolean isMatchedBy(Action action) {
        CallSequenceMatcher matcher = new CallSequenceMatcher(action);
        return actualCalls.stream().reduce(false, (acc, call) -> {
            matcher.match(call);
            return acc || matcher.isFullyMatched();
        }, Boolean::logicalOr);
    }



}
