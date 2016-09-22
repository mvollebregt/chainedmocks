package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class CallSequenceMatcher {

    // TODO: should a CallSequenceMatcher actually have a behaviour property?

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

    void match(ActualCall actualCall) {
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
            List<RecordedCall> recordedCalls = callRecorder.record(action, returnValues);
            if (actualCall.matches(recordedCalls.get(nextIndex))) {
                returnValues.add(actualCall.getReturnValue());
                fullyMatched = returnValues.size() == recordedCalls.size();
                break;
            }
        }
    }

    boolean matches(List<ActualCall> actualCalls) {
        return actualCalls.stream().reduce(false, (acc, call) -> {
            match(call);
            return acc || isFullyMatched();
        }, Boolean::logicalOr);
    }
}
