package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.AmbiguousExpectationsException;
import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class CallStubber {

    private final List<CallSequenceMatcher> matchers = new ArrayList<>();
    private final ValueProvider valueProvider = new IncrementingValueProvider();

    void addStub(ParameterisedAction action, ParameterisedFunction behaviour, Class[] wildcardTypes, CallRecorder callRecorder) {
        matchers.add(new CallSequenceMatcher(action, behaviour, wildcardTypes, callRecorder));
    }

    Object intercept(Object target, Method method, Object[] arguments) {
        Object defaultReturnValue = valueProvider.provide(method.getReturnType());
        List<Object> matches = match(target, method, arguments, defaultReturnValue);
        if (matches.size() > 1) {
            throw new AmbiguousExpectationsException();
        }
        return matches.size() == 1 ? matches.get(0) : defaultReturnValue;
    }

    private List<Object> match(Object target, Method method, Object[] arguments, Object defaultReturnValue) {
        matchers.forEach(callSequence -> callSequence.match(target, method, arguments, defaultReturnValue));
        return matchers.stream().filter(CallSequenceMatcher::isFullyMatched).map(CallSequenceMatcher::applyBehaviour).
                collect(Collectors.toList());
    }
}
