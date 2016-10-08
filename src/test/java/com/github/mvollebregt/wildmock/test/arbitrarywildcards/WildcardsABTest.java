package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsABTest {

    private final ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArguments;
    private Object returnValue;

    @Test
    public void testVerifyAB() {
        // when
        mock.actionAB("a", "b");
        // then
        verify(mock::actionAB, Object.class, Object.class);
    }

    @Test
    public void testVerifyWithAB() {
        // when
        mock.actionAB("a", "b");
        // then
        verify(mock::actionAB, Object.class, Object.class).with((a, b) -> a.equals("a") && b.equals("b"));
    }

    @Test
    public void testWhenAB() {
        // given
        when(mock::functionAB, Object.class, Object.class).then((a, b) -> Arrays.asList(a, b));
        // when
        usedArguments = mock.functionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }

    @Test
    public void testWhenABWithoutWildcards() {
        // given
        when(mock::functionAB, Object.class, Object.class).then(() -> 3);
        // when
        returnValue = mock.functionAB("a", "b");
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
    public void testTriggerAB() {
        // given
        trigger(mock::actionAB, Object.class, Object.class).
                then((a, b) -> usedArguments = Arrays.asList(a, b));
        // when
        mock.actionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }

    @Test
    public void testTriggerAWithoutWildcards() {
        // given
        trigger(mock::actionAB, Object.class, Object.class).then(() -> returnValue = 3);
        // when
        mock.actionAB("a", "b");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testTriggerWithAB() {
        // given
        trigger(mock::actionAB, Object.class, Object.class).
                with((a, b) -> a.equals("a") && b.equals("b")).
                then((a, b) -> usedArguments = Arrays.asList(a, b));
        // when
        mock.actionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }
}
