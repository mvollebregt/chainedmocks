package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.ChainedMocks;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import org.objenesis.Objenesis;

import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.any;

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
                .method(any()).intercept(MethodDelegation.to(this))
                .make()
                .load(ChainedMocks.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded());
    }

    @RuntimeType
    public Object intercept(@This Object target, @Origin Method method, @AllArguments Object[] arguments) {
        return MockContext.getMockContext().intercept(target, method, arguments);
    }
}
