package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.Action;

import java.lang.reflect.Method;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private Action behaviour;

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void setBehaviour(Action behaviour) {
        this.behaviour = behaviour;
    }

    public Object intercept(Object target, Method method, Object[] arguments) {
        behaviour.execute();
        return null;
    }
}
