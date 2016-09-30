package com.github.mvollebregt.chainedmocks.implementation.matching;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;
import com.github.mvollebregt.chainedmocks.implementation.base.CallRecorder;
import com.github.mvollebregt.chainedmocks.implementation.base.MethodCall;

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

// TODO: rewrite somewhat more
public class CallMatcher {

    private final ParameterisedAction action;
    private final ParameterisedFunction behaviour;
    private final CallRecorder callRecorder;
    private final WildcardMarkers wildcardMarkers;

    private final WildcardValues wildcards;
    private final List<Object> returnValues;

    private final List<MethodCall> remainingCalls;
    private final Set<MatchingValue> alreadyMatched = new HashSet<>();
    private final List<CallMatcher> continuedMatches = new ArrayList<>();

    public CallMatcher(ParameterisedAction action, ParameterisedFunction behaviour, Class[] wildcardTypes,
                       CallRecorder callRecorder) {
        this.action = action;
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

    private CallMatcher(CallMatcher supermatch, WildcardValues wildcards, List<Object> returnValues, List<MethodCall> remainingCalls) {
        this.action = supermatch.action;
        this.behaviour = supermatch.behaviour;
        this.callRecorder = supermatch.callRecorder;
        this.wildcardMarkers = supermatch.wildcardMarkers;
        this.wildcards = wildcards;
        this.returnValues = returnValues;
        this.remainingCalls = remainingCalls;
    }

    public Stream<Object[]> match(MethodCall methodCall) {

        Object target = methodCall.getTarget();
        Method method = methodCall.getMethod();
        Object[] arguments = methodCall.getArguments();
        Object returnValue = methodCall.getReturnValue();

        Stream<Object[]> matches = continuedMatches.stream().flatMap(submatch -> submatch.match(methodCall));

        if (matchesNextExpectedCall(target, method, arguments)) {
            int methodCallIndex = returnValues.size();
            WildcardValues matchedWildcards = wildcardMarkers.matchArguments(methodCallIndex, arguments);

            if (remainingCalls.size() == 1) {
                WildcardValues newWildcards = new WildcardValues(this.wildcards, matchedWildcards);
                matches = singletonList(newWildcards.toObjectArray()).stream();
            } else {
                if (alreadyMatched.add(new MatchingValue(returnValue, matchedWildcards))) {
                    continuedMatches.add(extend(returnValue, matchedWildcards));
                }
            }
        }
        return matches;
    }

    public Object applyBehaviour(Object[] arguments) {
        return behaviour.apply(arguments);
    }

    public boolean matches(List<MethodCall> actualCalls) {
        return actualCalls.stream().reduce(false, (alreadyMatched, methodCall) -> alreadyMatched ||
                match(methodCall).findAny().isPresent(), Boolean::logicalOr);
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

    private CallMatcher extend(Object returnValue, WildcardValues addedWildcards) {
        List<Object> newReturnValues = new ArrayList<>(returnValues);
        newReturnValues.add(returnValue);
        if (remainingCalls.get(0).getMethod().getReturnType().equals(Void.TYPE) && addedWildcards.isEmpty()) {
            return new CallMatcher(this, wildcards, newReturnValues, remainingCalls.stream().skip(1).collect(Collectors.toList()));
        } else {
            WildcardValues newWildcards = new WildcardValues(wildcards, addedWildcards);
            List<MethodCall> recordedCalls = callRecorder.record(action, newWildcards.toObjectArray(), new SimulatingCallInterceptor(newReturnValues));
            List<MethodCall> newRemainingCalls = recordedCalls.stream().skip(newReturnValues.size()).collect(Collectors.toList());
            return new CallMatcher(this, newWildcards, newReturnValues, newRemainingCalls);
        }
    }
}
