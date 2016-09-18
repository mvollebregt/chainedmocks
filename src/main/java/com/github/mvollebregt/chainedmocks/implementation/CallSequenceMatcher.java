package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CallSequenceMatcher {

    private final List<CallSequence> callSequences = new ArrayList<>();

    void addBehaviour(List<MethodCall> recordedCalls, Action behaviour) {
        // TODO: should we clear the mocks somewhere?
        this.callSequences.add(new CallSequence(recordedCalls, behaviour));
    }

    List<Action> match(MethodCall methodCall) {
        callSequences.forEach(callSequence -> callSequence.match(methodCall));
        return callSequences.stream().filter(CallSequence::isFullyMatched).map(CallSequence::getBehaviour).collect(Collectors.toList());
    }
}
