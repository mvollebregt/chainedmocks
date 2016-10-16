package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.ActionABC;
import com.github.mvollebregt.wildmock.function.FunctionABC;

public class VerifyABC<A, B, C> extends Verify {

    public static <A, B, C> VerifyABC<A, B, C> verify(ActionABC<A, B, C> expectedCalls,
                                                      Class<A> a, Class<B> b, Class<C> c) {
        VerifyABC<A, B, C> verify = new VerifyABC<>(expectedCalls, a, b, c);
        verify.check();
        return verify;
    }

    public void with(FunctionABC<A, B, C, Boolean> predicate) {
        super.with(predicate);
    }

    private VerifyABC(ActionABC<A, B, C> from, Class<A> a, Class<B> b, Class<C> c) {
        super(from, a, b, c);
    }
}