package com.github.mvollebregt.wildmock.implementation;

import com.github.mvollebregt.wildmock.api.MethodCall;
import com.github.mvollebregt.wildmock.exceptions.AmbiguousExpectationsException;
import com.github.mvollebregt.wildmock.function.VarargsCallable;
import com.github.mvollebregt.wildmock.implementation.base.CallInterceptor;
import com.github.mvollebregt.wildmock.implementation.base.CallRecorder;
import com.github.mvollebregt.wildmock.implementation.base.IncrementingValueProvider;
import com.github.mvollebregt.wildmock.implementation.matching.CallMatcher;

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

    void addStub(VarargsCallable action, VarargsCallable<Boolean> predicate,
                 VarargsCallable behaviour, Class[] wildcardTypes, CallRecorder callRecorderSwitcher) {
        matchers.add(new CallMatcher(action, predicate, behaviour, wildcardTypes, callRecorderSwitcher));
    }

    private Set<Object> match(MethodCall methodCall) {
        return matchers.stream().flatMap(callMatcher -> callMatcher.match(methodCall).
                filter(callMatcher::satisfiesPredicate).
                map(callMatcher::applyBehaviour).
                filter(dummy -> callMatcher.returnsValue())).
                collect(Collectors.toSet());
    }
}
