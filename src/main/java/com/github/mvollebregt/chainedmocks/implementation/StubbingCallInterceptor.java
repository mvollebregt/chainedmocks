package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.AmbiguousExpectationsException;
import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;
import com.github.mvollebregt.chainedmocks.function.ParameterisedFunction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class StubbingCallInterceptor implements CallInterceptor {

    private final List<CallMatcher> matchers = new ArrayList<>();
    private final IncrementingValueProvider valueProvider = new IncrementingValueProvider();
    private final List<MethodCall> actualCalls = new ArrayList<>();

    @Override
    public List<MethodCall> getRecordedCalls() {
        return actualCalls;
    }

    @Override
    public Object[] getWildcards() {
        return new Object[0];
    }

    @Override
    public Object intercept(Object target, Method method, Object[] arguments) {
        Object defaultReturnValue = valueProvider.provide(method.getReturnType());
        Set<Object> matches = match(new MethodCall(target, method, arguments, defaultReturnValue));
        if (matches.size() > 1) {
            throw new AmbiguousExpectationsException();
        }
        Object returnValue = matches.size() == 1 ? matches.iterator().next() : defaultReturnValue;
        actualCalls.add(new MethodCall(target, method, arguments, returnValue));
        return returnValue;
    }

    void addStub(ParameterisedAction action, ParameterisedFunction behaviour, Class[] wildcardTypes,
                 CallRecorder callRecorderSwitcher) {
        matchers.add(new CallMatcher(action, behaviour, wildcardTypes, callRecorderSwitcher));
    }

    private Set<Object> match(MethodCall methodCall) {
        return matchers.stream().flatMap(callSequence -> callSequence.match(methodCall).map(callSequence::applyBehaviour)).collect(Collectors.toSet());
    }
}
