package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import com.github.mvollebregt.chainedmocks.testhelpers.MockStatus;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StubBehaviourTest {

    @Test
    public void testVoid() {
        // given
        MockStatus status = new MockStatus("mock not called");
        ClassToBeMocked mock = mock(ClassToBeMocked.class);
        when(mock::action).then(() -> status.setMessage("mock called"));
        // when
        mock.action();
        // then
        assertEquals("mock called", status.getMessage());
    }

}
