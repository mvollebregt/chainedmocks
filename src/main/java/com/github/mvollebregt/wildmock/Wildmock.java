package com.github.mvollebregt.wildmock;

import com.github.mvollebregt.wildmock.fluentinterface.*;
import com.github.mvollebregt.wildmock.function.*;
import com.github.mvollebregt.wildmock.implementation.MockFactory;
import net.bytebuddy.ByteBuddy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public class Wildmock {

    private final static ByteBuddy byteBuddy = new ByteBuddy();
    private final static Objenesis objenesis = new ObjenesisStd();
    private final static MockFactory mockFactory = new MockFactory(objenesis, byteBuddy);

    public static <T> T mock(Class<T> classToBeMocked) {
        return mockFactory.createMock(classToBeMocked);
    }

    public static Verify verify(ActionX expectedCalls) {
        return Verify.verify(expectedCalls);
    }

    public static <A> VerifyA<A> verify(ActionA<A> expectedCalls, Class<A> a) {
        return VerifyA.verify(expectedCalls, a);
    }

    public static <A, B> VerifyAB<A, B> verify(ActionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        return VerifyAB.verify(expectedCalls, a, b);
    }

    public static <A, B, C> VerifyABC<A, B, C> verify(ActionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        return VerifyABC.verify(expectedCalls, a, b, c);
    }

    public static <R> When<R> when(FunctionX<R> expectedCalls) {
        return new When<>(expectedCalls);
    }

    public static <A, R> WhenA<A, R> when(FunctionA<A, R> expectedCalls, Class<A> a) {
        return new WhenA<>(expectedCalls, a);
    }

    public static <A, B, R> WhenAB<A, B, R> when(FunctionAB<A, B, R> expectedCalls, Class<A> a, Class<B> b) {
        return new WhenAB<>(expectedCalls, a, b);
    }

    public static <A, B, C, R> WhenABC<A, B, C, R> when(FunctionABC<A, B, C, R> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        return new WhenABC<>(expectedCalls, a, b, c);
    }

    public static Trigger trigger(ActionX expectedCalls) {
        return new Trigger(expectedCalls);
    }

    public static <A> TriggerA<A> trigger(ActionA<A> expectedCalls, Class<A> a) {
        return new TriggerA<>(expectedCalls, a);
    }

    public static <A, B> TriggerAB<A, B> trigger(ActionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        return new TriggerAB<>(expectedCalls, a, b);
    }

    public static <A, B, C> TriggerABC<A, B, C> trigger(ActionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        return new TriggerABC<>(expectedCalls, a, b, c);
    }
}
