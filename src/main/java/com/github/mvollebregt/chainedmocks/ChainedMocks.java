package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.fluentinterface.When;
import com.github.mvollebregt.chainedmocks.fluentinterface.WhenA;
import com.github.mvollebregt.chainedmocks.fluentinterface.WhenR;
import com.github.mvollebregt.chainedmocks.function.Action;
import com.github.mvollebregt.chainedmocks.implementation.MockFactory;
import net.bytebuddy.ByteBuddy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.github.mvollebregt.chainedmocks.implementation.MockContext.getMockContext;

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

    public static <T> WhenR<T> when(Supplier<T> expectedCalls) {
        return new WhenR<>(expectedCalls);
    }

    public static <A> WhenA<A> when(Consumer<A> expectedCalls, Class<A> a) {
        return new WhenA<>(expectedCalls, a);
    }

    public static void verify(Action expectedCalls) {
        if (!getMockContext().verify(params -> expectedCalls.execute())) {
            throw new VerificationException();
        }
    }
}
