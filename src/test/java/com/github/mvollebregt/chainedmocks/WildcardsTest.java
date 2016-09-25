package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.mock;
import static com.github.mvollebregt.chainedmocks.ChainedMocks.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

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

    @Test
    public void testStubPrimitiveWildcard() {
        // given
        when(mock::consume, Integer.class).then(wildcard -> status = "mock called with wildcard " + wildcard);
        // when
        mock.consume(3);
        // then
        assertEquals("mock called with wildcard 3", status);
    }

    @Test
    public void testWildcardUsedTwice_Success() {
        when(wildcard -> {
            mock.consume(wildcard);
            mock.consume(wildcard);
        }, String.class).then(wildcard -> status = "call sequence called with wildcard " + wildcard);
        // when
        mock.consume("argument");
        mock.consume("argument");
        // then
        assertEquals("call sequence called with wildcard argument", status);
    }

    @Test
    public void testWildcardUsedTwice_Failure() {
        when(wildcard -> {
            mock.consume(wildcard);
            mock.consume(wildcard);
        }, String.class).then(wildcard -> status = "call sequence called with wildcard " + wildcard);
        // when
        mock.consume("argument");
        mock.consume("argument");
        // then
        assertEquals("call sequence called with wildcard argument", status);
    }

    @Test
    public void testWildcardNotFound() {
        expectThrows(UnusedWildcardException.class, () ->
                when(param -> mock.action(), Integer.class).then(wildcard -> {
                }));
    }
}
