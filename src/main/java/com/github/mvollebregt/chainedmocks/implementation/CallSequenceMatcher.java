package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.UnusedWildcardException;
import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class CallSequenceMatcher {

    private final ParameterisedAction action;
    private final ParameterisedFunction behaviour;
    private final CallRecorder callRecorder;
    private final List<MethodCall> prerecordedCalls;
    private final Object[] initialWildcards;
    private final List<PartialMatch> partialMatches = new ArrayList<>();
    private PartialMatch foundMatch = null;

    CallSequenceMatcher(ParameterisedAction action, ParameterisedFunction behaviour, Class[] wildcardTypes,
                        CallRecorder callRecorder) {
        this.action = action;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
        this.prerecordedCalls = prerecord(wildcardTypes);
        this.initialWildcards = DefaultValueProvider.provideDefault(wildcardTypes);
    }

    boolean isFullyMatched() {
        return foundMatch != null;
    }

    Object applyBehaviour() {
        return isFullyMatched() ? behaviour.apply(foundMatch.getWildcards()) : null;
    }

    private List<MethodCall> prerecord(Class[] wildcardTypes) {
        // prerecord the action so we can find all wild cards
        ValueProvider valueProvider = new IncrementingValueProvider();
        List<MethodCall> prerecordedCalls = callRecorder.record(action, valueProvider.provide(wildcardTypes), valueProvider);
        List<Integer> unusedWildcardIndices = callRecorder.getUnusedWildcardIndices();
        if (!unusedWildcardIndices.isEmpty()) {
            throw new UnusedWildcardException(unusedWildcardIndices);
        }
        return prerecordedCalls;
    }

    void match(Object target, Method method, Object[] arguments, Object returnValue) {
        // discard a full match, if there is one
        if (isFullyMatched()) {
            partialMatches.remove(foundMatch);
            foundMatch = null;
        }
        // make sure we detect a new partial match, if there is one
        if (partialMatches.isEmpty() || partialMatches.get(partialMatches.size() - 1).nextIndex() != 0) {
            partialMatches.add(new PartialMatch(initialWildcards));
        }
        // find the first partial match that matches the new method call, and add to it
        for (PartialMatch partialMatch : partialMatches) {
            // do the target and method match?
            int nextIndex = partialMatch.nextIndex();
            MethodCall prerecordedCall = prerecordedCalls.get(nextIndex);
            if (prerecordedCall.matches(target, method)) {
                // if so, does the next call contain a wild card?
                prerecordedCall.getWildcardMarkers().forEach(pointer ->
                        partialMatch.setWildcard(pointer.getWildcardIndex(), arguments[pointer.getArgumentIndex()]));
                // if we use the newly found wild cards, do target, method and arguments match?
                // TODO: we should only rerecord if there are new wildcards!
                List<MethodCall> rerecordedCalls = callRecorder.record(
                        action, partialMatch.getWildcards(), partialMatch.getReturnValues());
                if (rerecordedCalls.get(nextIndex).matches(target, method, arguments)) {
                    partialMatch.addReturnValue(returnValue);
                    foundMatch = partialMatch.nextIndex() == rerecordedCalls.size() ? partialMatch : null;
                    break;
                }
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
