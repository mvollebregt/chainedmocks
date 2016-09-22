package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class CallSequenceMatcher {

    private final Action action;
    private final Supplier behaviour;
    private final CallRecorder callRecorder;
    private boolean fullyMatched = false;
    private final List<List<Object>> partialMatches = new ArrayList<>();

    CallSequenceMatcher(Action action, Supplier behaviour, CallRecorder callRecorder) {
        this.action = action;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
    }

    Supplier getBehaviour() {
        return behaviour;
    }

    boolean isFullyMatched() {
        return fullyMatched;
    }

    void match(Object target, Method method, Object[] arguments, Object returnValue) {
        // discard a full match, if there is one
        if (fullyMatched) {
            partialMatches.remove(0);
            fullyMatched = false;
        }
        // make sure we detect a new partial match, if there is one
        if (partialMatches.isEmpty() || partialMatches.get(partialMatches.size() - 1).size() != 0) {
            partialMatches.add(new ArrayList<>());
        }
        // find the first partial match that matches the new method call, and increase it
        for (List<Object> returnValues : partialMatches) {
            // is it a match?
            int nextIndex = returnValues.size();
            List<MethodCall> recordedCalls = callRecorder.record(action, returnValues);
            if (recordedCalls.get(nextIndex).matches(target, method, arguments)) {
                returnValues.add(returnValue);
                fullyMatched = returnValues.size() == recordedCalls.size();
                break;
            }
        }
    }

    boolean matches(List<MethodCall> actualCalls) {
        return actualCalls.stream().reduce(false, (alreadyMatched, call) -> {
            if (!alreadyMatched) {
                match(call.getTarget(), call.getMethod(), call.getArguments(), call.getReturnValue());
                return isFullyMatched();
            } else {
                return true;
            }
        }, Boolean::logicalOr);
    }
}
