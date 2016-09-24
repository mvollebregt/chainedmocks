package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class CallSequenceMatcher {

    private final ParameterisedAction action;
    private final ParameterisedFunction behaviour;
    private final CallRecorder callRecorder;
    private final WildcardMatcher wildcardMatcher;
    private final List<PartialMatch> partialMatches = new ArrayList<>();
    private PartialMatch foundMatch = null;

    CallSequenceMatcher(ParameterisedAction action, ParameterisedFunction behaviour, Class[] wildcardTypes, CallRecorder callRecorder) {
        this.action = action;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
        this.wildcardMatcher = WildcardMatcher.findWildcards(action, wildcardTypes, callRecorder);
    }

    boolean isFullyMatched() {
        return foundMatch != null;
    }

    Object applyBehaviour() {
        return isFullyMatched() ? behaviour.apply(foundMatch.getWildcards()) : null;
    }

    void match(Object target, Method method, Object[] arguments, Object returnValue) {
        // discard a full match, if there is one
        if (isFullyMatched()) {
            partialMatches.remove(foundMatch);
            foundMatch = null;
        }
        // make sure we detect a new partial match, if there is one
        if (partialMatches.isEmpty() || partialMatches.get(partialMatches.size() - 1).nextIndex() != 0) {
            partialMatches.add(new PartialMatch(wildcardMatcher.size()));
        }
        // find the first partial match that matches the new method call, and add to it
        for (PartialMatch partialMatch : partialMatches) {
            // did we find new wildcard values?
            wildcardMatcher.get(partialMatch.nextIndex(), target, method).forEach(wildcard ->
                    partialMatch.setWildcard(wildcard.getWildcardIndex(), arguments[wildcard.getArgumentNumber()]));
            // is it a match?
            List<MethodCall> recordedCalls = callRecorder.record(action, partialMatch.getWildcards(),
                    new PrerecordedValueProvider(partialMatch.getReturnValues()));
            if (recordedCalls.get(partialMatch.nextIndex()).matches(target, method, arguments)) {
                partialMatch.addReturnValue(returnValue);
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
