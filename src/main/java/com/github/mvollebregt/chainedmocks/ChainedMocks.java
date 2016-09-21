package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.fluentinterface.When;
import com.github.mvollebregt.chainedmocks.fluentinterface.When1;
import com.github.mvollebregt.chainedmocks.function.Action;
import com.github.mvollebregt.chainedmocks.implementation.*;
import net.bytebuddy.ByteBuddy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.function.Supplier;

public class ChainedMocks {

    private final static ByteBuddy byteBuddy = new ByteBuddy();
    private final static Objenesis objenesis = new ObjenesisStd();
    private final static MockFactory mockFactory = new MockFactory(objenesis, byteBuddy);

    public static <T> T mock(Class<T> classToBeMocked) {
        return mockFactory.createMock(classToBeMocked);
    }

    public static When when(Action expectedCalls) {
        return new When(expectedCalls);
    }

    public static <T> When1<T> when(Supplier<T> expectedCalls) {
        return new When1<>(expectedCalls);
    }

    public static void verify(Action expectedCalls) {
        if (!MockContext.getMockContext().getActualCalls().isMatchedBy(expectedCalls)) {
            throw new VerificationException();
        }
    }
}
