package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.fluentinterface.When;
import com.github.mvollebregt.chainedmocks.function.Action;
import com.github.mvollebregt.chainedmocks.implementation.MockContext;
import com.github.mvollebregt.chainedmocks.implementation.MockFactory;
import net.bytebuddy.ByteBuddy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

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

    public static void verify(Action expectedCalls) {
        MockContext.getMockContext().verify(expectedCalls);
    }
}
