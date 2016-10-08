package com.github.mvollebregt.wildmock.implementation.matching;

import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;
import com.github.mvollebregt.wildmock.implementation.base.CallRecorder;
import com.github.mvollebregt.wildmock.implementation.base.MethodCall;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class CallMatcher {

    private final ParameterisedAction action;
    private final ParameterisedFunction<Boolean> predicate;
    private final ParameterisedFunction behaviour;
    private final CallRecorder callRecorder;
    private final WildcardMarkers wildcardMarkers;

    private final WildcardValues wildcards;
    private final List<Object> returnValues;

    private final List<MethodCall> remainingCalls;
    private final Set<MatchedValue> alreadyMatched = new HashSet<>();
    private final List<CallMatcher> followingMatchers = new ArrayList<>();


    public CallMatcher(ParameterisedAction action, ParameterisedFunction<Boolean> predicate,
                       ParameterisedFunction behaviour, Class[] wildcardTypes, CallRecorder callRecorder) {
        this.action = action;
        this.predicate = predicate;
        this.behaviour = behaviour;
        this.callRecorder = callRecorder;
        WildcardMatchingCallInterceptor wildcardMatcher = new WildcardMatchingCallInterceptor(wildcardTypes);
        callRecorder.record(action, wildcardMatcher.getWildcards(), wildcardMatcher);
        wildcardMatcher.verifyAllWildcardsMatched();
        this.wildcardMarkers = wildcardMatcher.getWildcardMarkers();
        this.remainingCalls = wildcardMatcher.getRecordedCalls();
        this.wildcards = new WildcardValues(DefaultValueProvider.provideDefault(wildcardTypes));
        this.returnValues = emptyList();
    }

    private CallMatcher(CallMatcher precedingMatcher, Object returnValue, WildcardValues addedWildcards) {
        this.action = precedingMatcher.action;
        this.predicate = precedingMatcher.predicate;
        this.behaviour = precedingMatcher.behaviour;
        this.callRecorder = precedingMatcher.callRecorder;
        this.wildcardMarkers = precedingMatcher.wildcardMarkers;
        this.wildcards = precedingMatcher.wildcards.plus(addedWildcards);
        this.returnValues = Stream.concat(precedingMatcher.returnValues.stream(), Stream.of(returnValue)).collect(Collectors.toList());
        if (precedingMatcher.remainingCalls.get(0).getMethod().getReturnType().equals(Void.TYPE) && addedWildcards.isEmpty()) {
            remainingCalls = precedingMatcher.remainingCalls.stream().skip(1).collect(Collectors.toList());
        } else {
            List<MethodCall> recordedCalls = callRecorder.record(action, wildcards.toObjectArray(), new SimulatingCallInterceptor(returnValues));
            this.remainingCalls = recordedCalls.stream().skip(returnValues.size()).collect(Collectors.toList());
        }
    }

    public Object applyBehaviour(Object[] arguments) {
        return behaviour.apply(arguments);
    }

    public boolean satisfiesPredicate(Object[] arguments) {
        return predicate != null ? predicate.apply(arguments) : true;
    }

    public List<Object[]> matches(List<MethodCall> actualCalls) {
        return actualCalls.stream().flatMap(this::match).collect(Collectors.toList());
    }

    public Stream<Object[]> match(MethodCall methodCall) {

        Stream<Object[]> matches = followingMatchers.stream().
                flatMap(followingMatch -> followingMatch.match(methodCall));

        if (matchesNextExpectedCall(methodCall.getTarget(), methodCall.getMethod(), methodCall.getArguments())) {
            int methodCallIndex = returnValues.size();
            WildcardValues extraWildcards = wildcardMarkers.matchArguments(methodCallIndex, methodCall.getArguments());

            if (remainingCalls.size() == 1) {
                Object[] arguments = this.wildcards.plus(extraWildcards).toObjectArray();
                matches = singletonList(arguments).stream();
            } else {
                if (alreadyMatched.add(new MatchedValue(methodCall.getReturnValue(), extraWildcards))) {
                    followingMatchers.add(new CallMatcher(this, methodCall.getReturnValue(), extraWildcards));
                }
            }
        }
        return matches;
    }

    private boolean matchesNextExpectedCall(Object target, Method method, Object[] arguments) {
        int methodCallIndex = returnValues.size();
        Set<Integer> argumentIndicesForWildcards = wildcardMarkers.getArgumentIndicesForWildcards(methodCallIndex);
        MethodCall nextExpectedCall = remainingCalls.get(0);
        return target.equals(nextExpectedCall.getTarget()) &&
                method.equals(nextExpectedCall.getMethod()) &&
                IntStream.range(0, arguments.length).
                        filter(argumentIndex -> !argumentIndicesForWildcards.contains(argumentIndex)).
                        allMatch(argumentIndex ->
                                arguments[argumentIndex].equals(nextExpectedCall.getArguments()[argumentIndex]));
    }
}
