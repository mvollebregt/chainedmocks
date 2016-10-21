package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.exceptions.WithClauseNotSatisfiedException;
import com.github.mvollebregt.wildmock.function.ActionX;
import com.github.mvollebregt.wildmock.function.VarargsCallable;

import java.util.List;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

public class Verify {

    private final Class[] classes;
    private final VarargsCallable expectedCalls;
    private List<Object[]> matches;

    public static Verify verify(ActionX expectedCalls) {
        Verify verify = new Verify(expectedCalls);
        verify.check();
        return verify;
    }

    Verify(VarargsCallable expectedCalls, Class... classes) {
        this.classes = classes;
        this.expectedCalls = expectedCalls;
    }

    void with(VarargsCallable<Boolean> predicate) {
        if (!matches.stream().filter(predicate::apply).findAny().isPresent()) {
            throw new WithClauseNotSatisfiedException(matches);
        }
    }

    void check() {
        matches = getMockContext().verify(expectedCalls, classes);
    }
}
