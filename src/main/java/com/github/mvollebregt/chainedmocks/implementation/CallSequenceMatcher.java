package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class CallSequenceMatcher {

    private final List<MethodCall> methodCalls;
    private final List<Integer> partialMatches = new ArrayList<>();
    private final Supplier behaviour;

    CallSequenceMatcher(CallSequence callSequence) {
        this(callSequence, null);
    }

    CallSequenceMatcher(CallSequence expectedCalls, Supplier behaviour) {
        this.methodCalls = expectedCalls.getMethodCalls();
        this.behaviour = behaviour;
    }

    Supplier getBehaviour() {
        return behaviour;
    }

    boolean isFullyMatched() {
        return partialMatches.size() > 0 && partialMatches.get(0) == methodCalls.size();
    }

    void match(MethodCall actualCall) {
        // discard a full match, if there is one
        if (isFullyMatched()) {
            partialMatches.remove(0);
        }
        // make sure we detect a new partial match, if there is one
        if (partialMatches.isEmpty() || partialMatches.get(partialMatches.size() - 1) != 0) {
            partialMatches.add(0);
        }
        // find the first partial match that matches the new method call, and increase it
        int index;
        for (index = 0; index < partialMatches.size(); index++) {
            int matchedItemCount = partialMatches.get(index);
            if (actualCall.equals(methodCalls.get(matchedItemCount))) {
                partialMatches.set(index, matchedItemCount + 1);
                break;
            }
        }
    }
}
