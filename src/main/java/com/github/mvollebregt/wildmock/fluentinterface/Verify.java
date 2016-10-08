package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.function.ActionX;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

import java.util.List;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

public class Verify {

    private final Class[] classes;
    private final ParameterisedAction expectedCalls;
    private List<Object[]> matches;

    public static Verify verify(ActionX expectedCalls) {
        Verify verify = new Verify(ParameterisedAction.from(expectedCalls));
        verify.check();
        return verify;
    }

    Verify(ParameterisedAction expectedCalls, Class... classes) {
        this.classes = classes;
        this.expectedCalls = expectedCalls;
    }

    void with(ParameterisedFunction<Boolean> predicate) {
        if (!matches.stream().filter(predicate::apply).findAny().isPresent()) {
            throw new VerificationException();
        }
    }

    void check() {
        matches = getMockContext().verify(expectedCalls, classes);
        if (matches.isEmpty()) {
            throw new VerificationException();
        }
    }
}
