package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import static org.junit.jupiter.api.Assertions.fail;

public class ArbitraryWildcardsMockClass {

    public void functionA(Object a) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionAR(Object a) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public void functionAB(Object a, Object b) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionABR(Object a, Object b) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public void functionABC(Object a, Object b, Object c) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionABCR(Object a, Object b, Object c) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

}
