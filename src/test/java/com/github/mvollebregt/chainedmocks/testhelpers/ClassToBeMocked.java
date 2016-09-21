package com.github.mvollebregt.chainedmocks.testhelpers;

import static org.junit.jupiter.api.Assertions.fail;

public class ClassToBeMocked {

    public void action() {
        fail("Underlying methods on mocked class should not be called.");
    }

    public void otherAction() {
        fail("Underlying methods on mocked class should not be called.");
    }

    public void yetAnotherAction() {
        fail("Underlying methods on mocked class should not be called.");
    }

    public void consume(String argument) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public void consume(int passingInt) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public String provideString() {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public byte provideByte() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }


    public short provideShort() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public int provideInt() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public long provideLong() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public float provideFloat() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public double provideDouble() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public boolean provideBoolean() {
        fail("Underlying methods on mocked class should not be called.");
        return false;
    }

    public char provideChar() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }
}
