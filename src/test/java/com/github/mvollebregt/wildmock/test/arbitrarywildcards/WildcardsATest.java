package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsATest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArgument;
    private Object returnValue;

    @Test
    public void testWhenA() {
        // given
        when(mock::functionAR, Object.class).then(a -> a);
        // when
        usedArgument = mock.functionAR("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testWhenAWithoutWildcards() {
        // given
        when(mock::functionAR, Object.class).then(() -> 3);
        // when
        returnValue = mock.functionAR("a");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testWhenWithA() {
        // given
        when(mock::functionAR, Object.class).with(a -> a.equals("a")).then(a -> a);
        // when
        usedArgument = mock.functionAR("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testTriggerA() {
        // given
        trigger(mock::functionA, Object.class).then(a -> usedArgument = a);
        // when
        mock.functionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testTriggerAWithoutWildcards() {
        // given
        trigger(mock::functionA, Object.class).then(() -> returnValue = 3);
        // when
        mock.functionA("a");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testTriggerWithA() {
        // given
        trigger(mock::functionA, Object.class).with(a -> a.equals("a")).then(a -> usedArgument = a);
        // when
        mock.functionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testVerifyA() {
        // when
        mock.functionA("a");
        // then
        verify(mock::functionA, Object.class);
    }

    @Test
    public void testVerifyWithA() {
        // when
        mock.functionA("a");
        // then
        verify(mock::functionA, String.class).with(a -> a.equals("a"));
    }

}
