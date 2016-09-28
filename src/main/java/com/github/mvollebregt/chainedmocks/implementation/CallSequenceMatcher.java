package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.UnusedWildcardException;
import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CallSequenceMatcher {

    private final ParameterisedAction action;
    private final ParameterisedFunction behaviour;
    private final CallRecorder callRecorder;
    private final List<PartialMatch> partialMatches = new ArrayList<>();

    CallSequenceMatcher(ParameterisedAction action, ParameterisedFunction behaviour, Class[] wildcardTypes,
                        CallRecorder callRecorder) {
        this.action = action;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
        Object[] initialWildcards = DefaultValueProvider.provideDefault(wildcardTypes);
        WildcardMatchingCallInterceptor wildcardMatcher = prerecord(action, wildcardTypes);
        WildcardMarkers wildcardMarkers = wildcardMatcher.getWildcardMarkers();
        List<MethodCall> recordedCalls = wildcardMatcher.getRecordedCalls();
        partialMatches.add(new PartialMatch(initialWildcards, wildcardMarkers, recordedCalls));
    }

    Object applyBehaviour(Object[] arguments) {
        return behaviour.apply(arguments);
    }

    Stream<Object[]> match(MethodCall methodCall) {

        Map<Boolean, List<PartialMatch>> newMatches = partialMatches.stream().map(partialMatch ->
                partialMatch.newMatch(methodCall, action, callRecorder)).
                filter(Objects::nonNull).
                collect(Collectors.partitioningBy(PartialMatch::isFullmatch));

        partialMatches.addAll(newMatches.get(false));
        return newMatches.get(true).stream().map(PartialMatch::getWildcards);
    }

    boolean matches(List<MethodCall> actualCalls) {
        return actualCalls.stream().reduce(false, (alreadyMatched, methodCall) -> alreadyMatched ||
                match(methodCall).findAny().isPresent(), Boolean::logicalOr);
    }

    private WildcardMatchingCallInterceptor prerecord(ParameterisedAction action, Class[] wildcardTypes) {
        WildcardMatchingCallInterceptor wildcardMatcher = new WildcardMatchingCallInterceptor(wildcardTypes);
        callRecorder.record(action, wildcardMatcher);
        List<Integer> unusedWildcardIndices = wildcardMatcher.getUnusedWildcardIndices();
        if (!unusedWildcardIndices.isEmpty()) {
            throw new UnusedWildcardException(unusedWildcardIndices);
        }
        return wildcardMatcher;
    }
}
