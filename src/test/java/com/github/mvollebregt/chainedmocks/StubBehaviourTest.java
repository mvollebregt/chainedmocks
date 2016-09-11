package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StubBehaviourTest {

    private String status = "mock not called";
    private ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVoid_MockCalled() {
        // given
        when(mock::action).then(() -> status = "mock called");
        // when
        mock.action();
        // then
        assertEquals("mock called", status);
    }

    @Test
    public void testVoid_MockNotCalled() {
        // given
        when(mock::action).then(() -> status = "mock called");
        // then
        assertEquals("mock not called", status);
    }
}
