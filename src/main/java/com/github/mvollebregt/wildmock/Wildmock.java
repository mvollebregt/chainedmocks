package com.github.mvollebregt.wildmock;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.fluentinterface.*;
import com.github.mvollebregt.wildmock.function.*;
import com.github.mvollebregt.wildmock.implementation.MockFactory;
import net.bytebuddy.ByteBuddy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

public class Wildmock {

    private final static ByteBuddy byteBuddy = new ByteBuddy();
    private final static Objenesis objenesis = new ObjenesisStd();
    private final static MockFactory mockFactory = new MockFactory(objenesis, byteBuddy);

    public static <T> T mock(Class<T> classToBeMocked) {
        return mockFactory.createMock(classToBeMocked);
    }

    public static When whenVoid(FunctionX expectedCalls) {
        return new When(expectedCalls);
    }

    public static <A> WhenA<A> whenVoid(FunctionA<A> expectedCalls, Class<A> a) {
        return new WhenA<>(expectedCalls, a);
    }

    public static <A, B> WhenAB<A, B> whenVoid(FunctionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        return new WhenAB<>(expectedCalls, a, b);
    }

    public static <A, B, C> WhenABC<A, B, C> whenVoid(FunctionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        return new WhenABC<>(expectedCalls, a, b, c);
    }

    public static <R> WhenR<R> when(FunctionR<R> expectedCalls) {
        return new WhenR<>(expectedCalls);
    }

    public static <A, R> WhenAR<A, R> when(FunctionAR<A, R> expectedCalls, Class<A> a) {
        return new WhenAR<>(expectedCalls, a);
    }

    public static <A, B, R> WhenABR<A, B, R> when(FunctionABR<A, B, R> expectedCalls, Class<A> a, Class<B> b) {
        return new WhenABR<>(expectedCalls, a, b);
    }

    public static <A, B, C, R> WhenABCR<A, B, C, R> when(FunctionABCR<A, B, C, R> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        return new WhenABCR<>(expectedCalls, a, b, c);
    }

    public static void verify(FunctionX expectedCalls) {
        verify(ParameterisedAction.from(expectedCalls));
    }

    public static <A> VerifyA<A> verify(FunctionA<A> expectedCalls, Class<A> a) {
        verify(ParameterisedAction.from(expectedCalls), a);
        return new VerifyA<>(expectedCalls, a);
    }

    public static <A, B> void verify(FunctionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        verify(ParameterisedAction.from(expectedCalls), a, b);
    }

    public static <A, B, C> void verify(FunctionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        verify(ParameterisedAction.from(expectedCalls), a, b, c);
    }

    private static void verify(ParameterisedAction parameterisedAction,
                               Class... wildcardTypes) {
        if (!getMockContext().verify(parameterisedAction, arguments -> true, wildcardTypes)) {
            throw new VerificationException();
        }
    }
}
