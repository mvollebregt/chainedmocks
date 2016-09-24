package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.mock;
import static com.github.mvollebregt.chainedmocks.ChainedMocks.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);
    private String status = "mock not called";

    @Test
    public void testStubOneWildcard() {
        // given
        when(mock::consume, String.class).then(wildcard -> status = "mock called with wildcard " + wildcard);
        // when
        mock.consume("argument");
        // then
        assertEquals("mock called with wildcard argument", status);
    }
}
