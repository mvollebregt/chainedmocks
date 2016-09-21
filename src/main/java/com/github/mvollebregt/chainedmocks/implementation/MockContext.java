package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.AmbiguousExpectationsException;
import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final List<MethodCall> actualCalls = new ArrayList<>();
    private final List<CallSequenceMatcher> matchers = new ArrayList<>();
    private List<MethodCall> recordedCalls;
    private boolean recording = false;

    public static MockContext getMockContext() {
        return mockContext;
    }

    public CallSequence getActualCalls() {
        return new CallSequence(actualCalls);
    }

    public CallSequence record(Action action) {
        recordedCalls = new ArrayList<>();
        recording = true;
        action.execute();
        recording = false;
        return new CallSequence(recordedCalls);
    }

    public void stub(CallSequence expectedCalls, Action behaviour) {
        matchers.add(new CallSequenceMatcher(expectedCalls, behaviour));
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        (recording ? recordedCalls : actualCalls).add(new MethodCall(target, method));
        if (!recording) {
            List<Action> matches = match(new MethodCall(target, method));
            if (matches.size() == 1) {
                matches.forEach(Action::execute);
            } else if (matches.size() > 1) {
                throw new AmbiguousExpectationsException();
            }
        }
        return null;
    }

    private List<Action> match(MethodCall methodCall) {
        matchers.forEach(callSequence -> callSequence.match(methodCall));
        return matchers.stream().filter(CallSequenceMatcher::isFullyMatched).map(CallSequenceMatcher::getBehaviour).
                collect(Collectors.toList());
    }
}
