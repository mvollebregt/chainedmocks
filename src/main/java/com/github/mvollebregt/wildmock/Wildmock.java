package com.github.mvollebregt.wildmock;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.fluentinterface.When;
import com.github.mvollebregt.wildmock.fluentinterface.WhenA;
import com.github.mvollebregt.wildmock.fluentinterface.WhenAB;
import com.github.mvollebregt.wildmock.fluentinterface.WhenR;
import com.github.mvollebregt.wildmock.function.Action;
import com.github.mvollebregt.wildmock.function.ParameterisedAction;
import com.github.mvollebregt.wildmock.implementation.MockFactory;
import net.bytebuddy.ByteBuddy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

public class Wildmock {

    private final static ByteBuddy byteBuddy = new ByteBuddy();
    private final static Objenesis objenesis = new ObjenesisStd();
    private final static MockFactory mockFactory = new MockFactory(objenesis, byteBuddy);

    public static <T> T mock(Class<T> classToBeMocked) {
        return mockFactory.createMock(classToBeMocked);
    }

    public static When when(Action expectedCalls) {
        return new When(expectedCalls);
    }

    public static <T> WhenR<T> when(Supplier<T> expectedCalls) {
        return new WhenR<>(expectedCalls);
    }

    public static <A> WhenA<A> when(Consumer<A> expectedCalls, Class<A> a) {
        return new WhenA<>(expectedCalls, a);
    }

    public static <A, B> WhenAB<A, B> when(BiConsumer<A, B> expectedCalls, Class<A> a, Class<B> b) {
        return new WhenAB<>(expectedCalls, a, b);
    }

    public static void verify(Action expectedCalls) {
        verify(ParameterisedAction.from(expectedCalls));
    }

    public static <A> void verify(Consumer<A> expectedCalls, Class<A> a) {
        verify(ParameterisedAction.from(expectedCalls), a);
    }

    public static <A, B> void verify(BiConsumer<A, B> expectedCalls, Class<A> a, Class<B> b) {
        verify(ParameterisedAction.from(expectedCalls), a, b);
    }

    private static void verify(ParameterisedAction parameterisedAction, Class... wildcardTypes) {
        if (!getMockContext().verify(parameterisedAction, wildcardTypes)) {
            throw new VerificationException();
        }
    }
}
