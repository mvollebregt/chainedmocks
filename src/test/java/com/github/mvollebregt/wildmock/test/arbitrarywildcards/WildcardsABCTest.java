package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsABCTest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArguments;
    private Object returnValue;

    @Test
    public void testVerifyABC() {
        // when
        mock.actionABC("a", "b", "c");
        // then
        verify(mock::actionABC, Object.class, Object.class, Object.class);
    }

    @Test
    public void testVerifyWithABC() {
        // when
        mock.actionABC("a", "b", "c");
        // then
        verify(mock::actionABC, Object.class, Object.class, Object.class).
                with((a, b, c) -> a.equals("a") && b.equals("b") && c.equals("c"));
    }

    @Test
    public void testWhenABC() {
        // given
        when(mock::functionABC, Object.class, Object.class, Object.class).then((a, b, c) -> Arrays.asList(a, b, c));
        // when
        usedArguments = mock.functionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }

    @Test
    public void testWhenABCWithoutWildcards() {
        // given
        when(mock::functionABC, Object.class, Object.class, Object.class).then(() -> 3);
        // when
        returnValue = mock.functionABC("a", "b", "c");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testWhenWithAB() {
        // given
        when(mock::functionAB, Object.class, Object.class).
                with((a, b) -> a.equals("a") && b.equals("b")).
                then((a, b) -> Arrays.asList(a, b));
        // when
        usedArguments = mock.functionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }

    @Test
    public void testTriggerABC() {
        // given
        trigger(mock::actionABC, Object.class, Object.class, Object.class).
                then((a, b, c) -> usedArguments = Arrays.asList(a, b, c));
        // when
        mock.actionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }

    @Test
    public void testTriggerWithABC() {
        // given
        trigger(mock::actionABC, Object.class, Object.class, Object.class).
                with((a, b, c) -> a.equals("a") && b.equals("b") && c.equals("c")).
                then((a, b, c) -> usedArguments = Arrays.asList(a, b, c));
        // when
        mock.actionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }
}
