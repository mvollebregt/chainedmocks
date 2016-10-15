package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionAB;
import com.github.mvollebregt.wildmock.function.FunctionAB;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class VerifyAB<A, B> extends Verify {

    public static <A, B> VerifyAB<A, B> verify(ActionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        VerifyAB<A, B> verify = new VerifyAB<>(expectedCalls, a, b);
        verify.check();
        return verify;
    }

    public void with(FunctionAB<A, B, Boolean> predicate) {
        super.with(predicate);
    }

    private VerifyAB(ParameterisedFunction from, Class<A> a, Class<B> b) {
        super(from, a, b);
    }
}