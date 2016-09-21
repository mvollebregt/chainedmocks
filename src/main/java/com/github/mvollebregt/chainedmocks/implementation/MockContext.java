package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.AmbiguousExpectationsException;
import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final List<MethodCall> actualCalls = new ArrayList<>();
    private final List<CallSequenceMatcher> matchers = new ArrayList<>();
    private List<MethodCall> recordedCalls;
    private boolean recording = false;
    private ValueProvider recordingValueProvider;
    private final ValueProvider testValueProvider = new IncrementingValueProvider(1);

    public static MockContext getMockContext() {
        return mockContext;
    }

    public ActualCalls getActualCalls() {
        return new ActualCalls(actualCalls);
    }

    public void stub(Action action, Supplier behaviour) {
        matchers.add(new CallSequenceMatcher(action, behaviour));
    }

    List<MethodCall> record(Action action, ValueProvider valueProvider) {
        this.recordingValueProvider = valueProvider;
        recordedCalls = new ArrayList<>();
        recording = true;
        action.execute();
        recording = false;
        return recordedCalls;
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        MethodCall methodCall = new MethodCall(target, method, arguments);
        if (recording) {
            recordedCalls.add(methodCall);
            return recordingValueProvider.provide(method.getReturnType());
        } else {
            List<Supplier> matches = match(methodCall);
            if (matches.size() > 1) {
                throw new AmbiguousExpectationsException();
            }
            Object returnValue = matches.size() == 1 ? matches.get(0).get() : testValueProvider.provide(method.getReturnType());
            actualCalls.add(methodCall.withReturnValue(returnValue));
            return returnValue;
        }
    }

    private List<Supplier> match(MethodCall methodCall) {
        matchers.forEach(callSequence -> callSequence.match(methodCall));
        return matchers.stream().filter(CallSequenceMatcher::isFullyMatched).map(CallSequenceMatcher::getBehaviour).
                collect(Collectors.toList());
    }
}
