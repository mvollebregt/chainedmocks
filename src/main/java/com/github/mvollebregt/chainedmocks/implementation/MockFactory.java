package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.ChainedMocks;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.bind.annotation.*;
import org.objenesis.Objenesis;

import java.lang.reflect.Method;

import static net.bytebuddy.implementation.MethodDelegation.to;
import static net.bytebuddy.matcher.ElementMatchers.*;

public class MockFactory {

    private final Objenesis objenesis;
    private final ByteBuddy byteBuddy;

    public MockFactory(Objenesis objenesis, ByteBuddy byteBuddy) {
        this.objenesis = objenesis;
        this.byteBuddy = byteBuddy;
    }

    public <T> T createMock(Class<T> clazz) {
        return objenesis.newInstance(byteBuddy
                .subclass(clazz)
                .name("Mock" + clazz.getSimpleName())
                .method(any()).intercept(to(MockMethodInterceptor.class))
                .method(isHashCode()).intercept(to(HashCodeInterceptor.class))
                .method(isEquals()).intercept(to(EqualsInterceptor.class))
                .make()
                .load(ChainedMocks.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded());
    }

    public static class MockMethodInterceptor {
        @RuntimeType
        public static Object intercept(@This Object target, @Origin Method method, @AllArguments Object[] arguments) {
            return MockContext.getMockContext().intercept(target, method, arguments);
        }
    }

    public static class HashCodeInterceptor {
        public static int intercept(@This Object target) {
            return System.identityHashCode(target);
        }
    }

    public static class EqualsInterceptor {
        public static boolean intercept(@This Object target, @Argument(0) Object other) {
            return target == other;
        }
    }
}
