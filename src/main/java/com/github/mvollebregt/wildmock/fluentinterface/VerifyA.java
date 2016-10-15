package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionA;
import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class VerifyA<A> extends Verify {

    public static <A> VerifyA<A> verify(ActionA<A> expectedCalls, Class<A> a) {
        VerifyA<A> verify = new VerifyA<>(ParameterisedFunction.from(expectedCalls), a);
        verify.check();
        return verify;
    }

    public void with(FunctionA<A, Boolean> predicate) {
        super.with(ParameterisedFunction.from(predicate));
    }

    private VerifyA(ParameterisedFunction from, Class<A> a) {
        super(from, a);
    }
}