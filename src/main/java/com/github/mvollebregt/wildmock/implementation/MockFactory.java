package com.github.mvollebregt.wildmock.implementation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.bind.annotation.*;
import org.objenesis.Objenesis;

import java.lang.reflect.Method;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;
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
                .method(isToString()).intercept(to(ToStringInterceptor.class))
                .make()
                .load(MockFactory.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded());
    }

    @SuppressWarnings("WeakerAccess")
    public static class MockMethodInterceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@This Object target, @Origin Method method, @AllArguments Object[] arguments) {
            return getMockContext().getCurrentInterceptor().intercept(target, method, arguments);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class HashCodeInterceptor {
        @SuppressWarnings("unused")
        public static int intercept(@This Object target) {
            return System.identityHashCode(target);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class EqualsInterceptor {
        @SuppressWarnings("unused")
        public static boolean intercept(@This Object target, @Argument(0) Object other) {
            return target == other;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static class ToStringInterceptor {
        @SuppressWarnings("unused")
        public static String intercept(@This Object target) {
            return String.format("%s@%s", target.getClass().getSimpleName(), System.identityHashCode(target));
        }
    }
}
