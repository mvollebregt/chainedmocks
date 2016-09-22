package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.AmbiguousExpectationsException;
import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class CallStubber {

    private final List<CallSequenceMatcher> matchers = new ArrayList<>();
    private final ValueProvider valueProvider = new IncrementingValueProvider(1);

    void addStub(Action action, Supplier behaviour, CallRecorder callRecorder) {
        matchers.add(new CallSequenceMatcher(action, behaviour, callRecorder));
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        // TODO: reorganise this a bit
        Object potentialReturnValue = valueProvider.provide(method.getReturnType());
        List<Supplier> matches = match(new ActualCall(target, method, arguments, potentialReturnValue));
        if (matches.size() > 1) {
            throw new AmbiguousExpectationsException();
        }
        return matches.size() == 1 ? matches.get(0).get() : potentialReturnValue;
    }

    private List<Supplier> match(ActualCall actualCall) {
        matchers.forEach(callSequence -> callSequence.match(actualCall));
        return matchers.stream().filter(CallSequenceMatcher::isFullyMatched).map(CallSequenceMatcher::getBehaviour).
                collect(Collectors.toList());
    }
}
