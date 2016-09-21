package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

class CallSequenceMatcher {

    private final Action action;
    private final Supplier behaviour;
    private boolean fullyMatched = false;
    private final List<List<Object>> partialMatches = new ArrayList<>();

    CallSequenceMatcher(Action action) {
        this(action, null);
    }

    CallSequenceMatcher(Action action, Supplier behaviour) {
        this.action = action;
        this.behaviour = behaviour;
    }

    Supplier getBehaviour() {
        return behaviour;
    }

    boolean isFullyMatched() {
        return fullyMatched;
    }

    void match(MethodCall actualCall) {
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
            List<MethodCall> recordedCalls = MockContext.getMockContext().record(action, new PrerecordedValueProvider(returnValues));
            if (actualCall.equals(recordedCalls.get(nextIndex))) {
                returnValues.add(actualCall.getReturnValue());
                fullyMatched = returnValues.size() == recordedCalls.size();
                break;
            }
        }
    }
}
