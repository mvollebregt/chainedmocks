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
    private final List<PartialMatch> partialMatches = new ArrayList<>();
    private PartialMatch foundMatch = null;

    CallSequenceMatcher(Action action, Supplier behaviour, CallRecorder callRecorder) {
        this.action = action;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
    }

    Supplier getBehaviour() {
        return behaviour;
    }

    boolean isFullyMatched() {
        return foundMatch != null;
    }

    void match(Object target, Method method, Object[] arguments, Object returnValue) {
        // discard a full match, if there is one
        if (isFullyMatched()) {
            partialMatches.remove(foundMatch);
            foundMatch = null;
        }
        // make sure we detect a new partial match, if there is one
        if (partialMatches.isEmpty() || partialMatches.get(partialMatches.size() - 1).nextIndex() != 0) {
            partialMatches.add(new PartialMatch());
        }
        // find the first partial match that matches the new method call, and add to it
        for (PartialMatch partialMatch : partialMatches) {
            // is it a match?
            List<MethodCall> recordedCalls = callRecorder.record(action, partialMatch.getReturnValues());
            if (recordedCalls.get(partialMatch.nextIndex()).matches(target, method, arguments)) {
                partialMatch.add(returnValue);
                foundMatch = partialMatch.nextIndex() == recordedCalls.size() ? partialMatch : null;
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
