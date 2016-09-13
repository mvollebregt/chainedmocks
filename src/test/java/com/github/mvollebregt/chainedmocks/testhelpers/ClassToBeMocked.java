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
}
