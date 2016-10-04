package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionA;
import com.github.mvollebregt.wildmock.function.FunctionAR;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.function.ParameterisedFunction;

public class VerifyA<A> extends Verify {

    public VerifyA(FunctionA<A> expectedCalls, Class<A> a) {
        super(ParameterisedAction.from(expectedCalls), a);
    }

    public void with(FunctionAR<A, Boolean> predicate) {
        super.with(ParameterisedFunction.from(predicate));
    }
}