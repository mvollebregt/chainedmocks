package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CallSequenceMatcher {

    private List<CallSequence> initialSequences = new ArrayList<>();
    private List<CallSequence> partialMatches = new ArrayList<>();

    void addBehaviour(List<MethodCall> recordedCalls, Action behaviour) {
        partialMatches.clear();
        // TODO: we should not clear the partial matches at this point, but somewhere else!
        // TODO: when should we clear the initial sequences?
        this.initialSequences.add(new CallSequence(recordedCalls, behaviour));
    }

    List<Action> match(MethodCall methodCall) {
        partialMatches = Stream.concat(initialSequences.stream(), partialMatches.stream()).map(
                callSequence -> callSequence.match(methodCall)).filter(Objects::nonNull).collect(Collectors.toList());
        return partialMatches.stream().filter(CallSequence::isFullyMatched).
                map(CallSequence::getBehaviour).collect(Collectors.toList());
    }
}
