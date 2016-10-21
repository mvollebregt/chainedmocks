package com.github.mvollebregt.wildmock.implementation.matching;

import com.github.mvollebregt.wildmock.api.MethodCall;
import com.github.mvollebregt.wildmock.api.PartialMatch;
import com.github.mvollebregt.wildmock.function.VarargsCallable;
import com.github.mvollebregt.wildmock.implementation.base.CallRecorder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class CallMatcher {

    private final VarargsCallable action;
    private final VarargsCallable<Boolean> predicate;
    private final VarargsCallable behaviour;
    private final CallRecorder callRecorder;
    private final WildcardMarkers wildcardMarkers;

    private final Class[] wildcardTypes;
    private final WildcardValues wildcards;

    private final List<MethodCall> observedCalls;
    private MethodCall mismatch;
    private final List<MethodCall> remainingCalls;
    private final Set<MatchedValue> alreadyMatched = new HashSet<>();
    private final List<CallMatcher> followingMatchers = new ArrayList<>();

    public CallMatcher(VarargsCallable action, VarargsCallable<Boolean> predicate,
                       VarargsCallable behaviour, Class[] wildcardTypes, CallRecorder callRecorder) {
        this.action = action;
        this.predicate = predicate;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
        this.wildcardTypes = wildcardTypes;
        this.wildcards = new WildcardValues();
        SimulatingCallInterceptor wildcardMatcher = new SimulatingCallInterceptor(wildcardTypes, wildcards, emptyList());
        callRecorder.record(action, wildcardMatcher.getWildcards(), wildcardMatcher);
        this.wildcardMarkers = wildcardMatcher.getWildcardMarkers();
        this.remainingCalls = wildcardMatcher.getRecordedCalls();
        this.observedCalls = emptyList();
    }

    private CallMatcher(CallMatcher precedingMatcher, MethodCall methodCall, WildcardValues addedWildcards) {
        this.action = precedingMatcher.action;
        this.predicate = precedingMatcher.predicate;
        this.behaviour = precedingMatcher.behaviour;
        this.callRecorder = precedingMatcher.callRecorder;
        this.wildcardTypes = precedingMatcher.wildcardTypes;
        this.wildcards = precedingMatcher.wildcards.plus(addedWildcards);
        this.observedCalls = Stream.concat(precedingMatcher.observedCalls.stream(), Stream.of(methodCall)).collect(toList());
        if (precedingMatcher.remainingCalls.get(0).getMethod().getReturnType().equals(Void.TYPE) && addedWildcards.isEmpty()) {
            remainingCalls = precedingMatcher.remainingCalls.stream().skip(1).collect(toList());
            wildcardMarkers = precedingMatcher.wildcardMarkers;
        } else {
            SimulatingCallInterceptor wildcardMatcher = new SimulatingCallInterceptor(wildcardTypes, wildcards,
                    observedCalls.stream().map(MethodCall::getReturnValue).collect(toList()));
            List<MethodCall> recordedCalls = callRecorder.record(action, wildcardMatcher.getWildcards(), wildcardMatcher);
            this.wildcardMarkers = wildcardMatcher.getWildcardMarkers();
            this.remainingCalls = recordedCalls.stream().skip(observedCalls.size()).collect(toList());
        }
    }

    public Object applyBehaviour(Object[] arguments) {
        return behaviour.apply(arguments);
    }

    public boolean returnsValue() {
        return behaviour.returnsValue();
    }

    public boolean satisfiesPredicate(Object[] arguments) {
        return predicate != null ? predicate.apply(arguments) : true;
    }

    public List<Object[]> matches(List<MethodCall> actualCalls) {
        return actualCalls.stream().flatMap(this::match).collect(toList());
    }

    public PartialMatch closestMatch() {
        return followingMatchers.isEmpty() ? new PartialMatch(observedCalls, mismatch, remainingCalls) :
                followingMatchers.stream().map(CallMatcher::closestMatch).
                        max((matcher1, matcher2) -> matcher2.getObservedMethodCalls().size() -
                                matcher1.getObservedMethodCalls().size()).
                        orElse(null);
    }

    public Stream<Object[]> match(MethodCall methodCall) {

        List<Object[]> matches = followingMatchers.stream().
                flatMap(followingMatch -> followingMatch.match(methodCall)).collect(toList());

        int methodCallIndex = observedCalls.size();
        MethodCall nextExpectedCall = remainingCalls.get(0);

        if (methodCall.getTarget().equals(nextExpectedCall.getTarget())) {
            if (methodCall.getMethod().equals(nextExpectedCall.getMethod())) {
                if (argumentsMatch(methodCall, nextExpectedCall)) {

                    WildcardValues extraWildcards = wildcardMarkers.matchArguments(methodCallIndex, methodCall.getArguments());
                    CallMatcher newMatcher = new CallMatcher(this, methodCall, extraWildcards);

                    if (newMatcher.remainingCalls.size() == 0) {
                        Object[] arguments = newMatcher.wildcards.toObjectArray();
                        matches = singletonList(arguments);
                    } else {
                        if (alreadyMatched.add(new MatchedValue(methodCall.getReturnValue(), extraWildcards))) {
                            followingMatchers.add(newMatcher);
                        }
                    }
                } else {
                    mismatch = methodCall;
                }
            }
        }

        return matches.stream();
    }

    private boolean argumentsMatch(MethodCall methodCall, MethodCall nextExpectedCall) {
        int methodCallIndex = observedCalls.size();
        Set<Integer> argumentIndicesForWildcards = wildcardMarkers.getArgumentIndicesForWildcards(methodCallIndex);

        return IntStream.range(0, methodCall.getArguments().length).
                        filter(argumentIndex -> !argumentIndicesForWildcards.contains(argumentIndex)).
                        allMatch(argumentIndex ->
                                methodCall.getArguments()[argumentIndex].equals(
                                        nextExpectedCall.getArguments()[argumentIndex]));
    }
}
