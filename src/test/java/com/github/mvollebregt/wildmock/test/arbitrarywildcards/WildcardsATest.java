package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsATest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArgument;
    private Object returnValue;

    @Test
    public void testVerifyA() {
        // when
        mock.actionA("a");
        // then
        verify(mock::actionA, Object.class);
    }

    @Test
    public void testVerifyWithA() {
        // when
        mock.actionA("a");
        // then
        verify(mock::actionA, String.class).with(a -> a.equals("a"));
    }

    @Test
    public void testWhenA() {
        // given
        when(mock::functionA, Object.class).then(a -> a);
        // when
        usedArgument = mock.functionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testWhenAWithoutWildcards() {
        // given
        when(mock::functionA, Object.class).then(() -> 3);
        // when
        returnValue = mock.functionA("a");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testWhenWithA() {
        // given
        when(mock::functionA, Object.class).with(a -> a.equals("a")).then(a -> a);
        // when
        usedArgument = mock.functionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testTriggerA() {
        // given
        trigger(mock::actionA, Object.class).then(a -> usedArgument = a);
        // when
        mock.actionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testTriggerAWithoutWildcards() {
        // given
        trigger(mock::actionA, Object.class).then(() -> returnValue = 3);
        // when
        mock.actionA("a");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testTriggerWithA() {
        // given
        trigger(mock::actionA, Object.class).with(a -> a.equals("a")).then(a -> usedArgument = a);
        // when
        mock.actionA("a");
        // then
        assertEquals("a", usedArgument);
    }
}
