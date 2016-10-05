package com.github.mvollebregt.wildmock.test;

import com.github.mvollebregt.wildmock.exceptions.UnusedWildcardException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.trigger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class WildcardsTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);
    private String status = "mock not called";

    @Test
    public void testStubOneWildcard() {
        // given
        trigger(mock::consume, String.class).then(wildcard -> status = "mock called with wildcard " + wildcard);
        // when
        mock.consume("argument");
        // then
        assertEquals("mock called with wildcard argument", status);
    }

    @Test
    public void testStubPrimitiveWildcard() {
        // given
        trigger(mock::consume, Integer.class).then(wildcard -> status = "mock called with wildcard " + wildcard);
        // when
        mock.consume(3);
        // then
        assertEquals("mock called with wildcard 3", status);
    }

    @Test
    public void testWildcardUsedTwice_Success() {
        trigger(wildcard -> {
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
        trigger(wildcard -> {
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
                trigger(param -> mock.action(), Integer.class).then(wildcard -> {
                }));
    }

    @Test
    public void testDifferentReturnValue() {
        // given
        trigger((wildcard) -> {
            mock.action();
            int value = mock.identityFunction(wildcard);
            mock.consume(value);
        }, Integer.class).then(() -> status = "call sequence called");
        // when
        mock.action();
        mock.identityFunction(1); // first call is matched, "1" is taken as wildcard
        int secondReturnValue = mock.identityFunction(2); // however, "2" is the actual wildcard
        mock.consume(secondReturnValue);
        // then
        assertEquals("call sequence called", status);
    }
}
