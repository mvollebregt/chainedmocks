package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.function.ActionX;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import java.util.List;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

public class Verify {

    private final List<Object[]> matches;

    // TODO: replace constructor with static method?
    public Verify(ActionX expectedCalls) {
        this(ParameterisedAction.from(expectedCalls));
    }

    Verify(ParameterisedAction expectedCalls, Class... classes) {
        matches = getMockContext().verify(expectedCalls, classes);
        if (matches.isEmpty()) {
            throw new VerificationException();
        }
    }

    @SuppressWarnings("unchecked")
    void with(ParameterisedFunction<Boolean> predicate) {
        if (!matches.stream().filter(predicate::apply).findAny().isPresent()) {
            throw new VerificationException();
        }
    }
}
