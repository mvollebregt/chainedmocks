package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CallSequenceMatcher {

    private List<CallSequence> initialSequences = new ArrayList<>();
    private List<CallSequence> partialMatches = new ArrayList<>();

    void addBehaviour(List<MethodCall> recordedCalls, Action behaviour) {
        // TODO: should we clear the mocks somewhere?
        this.initialSequences.add(new CallSequence(recordedCalls, behaviour));
    }

    Set<Action> match(MethodCall methodCall) {
        Map<Boolean, List<CallSequence>> matches =
                Stream.concat(initialSequences.stream(), partialMatches.stream()).
                        map(callSequence -> callSequence.match(methodCall)).
                        filter(Objects::nonNull).
                        collect(Collectors.partitioningBy(CallSequence::isFullyMatched));
        partialMatches = matches.get(false);
        return matches.get(true).stream().map(CallSequence::getBehaviour).collect(Collectors.toSet());
    }
}
