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

    public <T> T provide(Class<T> clazz) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public int provideInt() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }
}
