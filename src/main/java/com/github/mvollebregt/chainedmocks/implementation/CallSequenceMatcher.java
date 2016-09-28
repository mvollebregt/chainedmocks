package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.UnusedWildcardException;
import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.util.List;
import java.util.stream.Stream;

class CallSequenceMatcher {

    private final ParameterisedAction action;
    private final ParameterisedFunction behaviour;
    private final CallRecorder callRecorder;
    private final PartialMatch partialMatch;

    CallSequenceMatcher(ParameterisedAction action, ParameterisedFunction behaviour, Class[] wildcardTypes,
                        CallRecorder callRecorder) {
        this.action = action;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
        Object[] initialWildcards = DefaultValueProvider.provideDefault(wildcardTypes);
        WildcardMatchingCallInterceptor wildcardMatcher = prerecord(action, wildcardTypes);
        WildcardMarkers wildcardMarkers = wildcardMatcher.getWildcardMarkers();
        List<MethodCall> recordedCalls = wildcardMatcher.getRecordedCalls();
        partialMatch = new PartialMatch(initialWildcards, wildcardMarkers, recordedCalls);
    }

    Object applyBehaviour(Object[] arguments) {
        return behaviour.apply(arguments);
    }

    Stream<Object[]> match(MethodCall methodCall) {
        return partialMatch.match(methodCall, action, callRecorder);
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
