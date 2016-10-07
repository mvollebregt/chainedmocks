package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import static org.junit.jupiter.api.Assertions.fail;

public class ArbitraryWildcardsMockClass {

    public void actionA(Object a) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionA(Object a) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public void actionAB(Object a, Object b) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionAB(Object a, Object b) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public void actionABC(Object a, Object b, Object c) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionABC(Object a, Object b, Object c) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

}
