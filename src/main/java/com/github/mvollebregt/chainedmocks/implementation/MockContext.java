package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.AmbiguousExpectationsException;
import com.github.mvollebregt.chainedmocks.VerificationException;
import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final List<MethodCall> actualCalls = new ArrayList<>();

    private final Map<CallSequence, Action> stubs = new HashMap<>();

    private List<MethodCall> recordedCalls;

    private boolean recording = false;

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void stub(Action expectedCalls, Action behaviour) {
        stubs.put(new CallSequence(record(expectedCalls)), behaviour);
    }

    public void verify(Action action) {
        List<MethodCall> expectedCalls = record(action);
        CallSequence callSequence = new CallSequence(expectedCalls);
        boolean matched = actualCalls.stream().reduce(false, (acc, call) -> {
            callSequence.match(call);
            return acc || callSequence.isFullyMatched();
        }, Boolean::logicalOr);
        if (!matched) {
            throw new VerificationException();
        }
    }

    private List<MethodCall> record(Action action) {
        recordedCalls = new ArrayList<>();
        recording = true;
        action.execute();
        recording = false;
        return recordedCalls;
    }

    private List<Action> match(MethodCall methodCall) {
        stubs.keySet().forEach(callSequence -> callSequence.match(methodCall));
        return stubs.entrySet().stream().filter(entry -> entry.getKey().isFullyMatched()).map(Map.Entry::getValue).
                collect(Collectors.toList());

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
}
