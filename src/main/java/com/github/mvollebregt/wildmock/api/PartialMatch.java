package com.github.mvollebregt.wildmock.api;

import java.util.List;

public class PartialMatch {

    private final List<MethodCall> observedMethodCalls;
    private final List<MethodCall> remainingMethodCalls;

    public PartialMatch(List<MethodCall> observedMethodCalls, List<MethodCall> remainingMethodCalls) {
        this.observedMethodCalls = observedMethodCalls;
        this.remainingMethodCalls = remainingMethodCalls;
    }

    public List<MethodCall> getObservedMethodCalls() {
        return observedMethodCalls;
    }

    public List<MethodCall> getRemainingMethodCalls() {
        return remainingMethodCalls;
    }
}
