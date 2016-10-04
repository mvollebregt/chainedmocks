package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsATest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArgument;

    @Test
    public void testWhenA() {
        // given
        whenVoid(mock::functionA, Object.class).then(a -> usedArgument = a);
        // when
        mock.functionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testWhenAR() {
        // given
        when(mock::functionAR, Object.class).then(a -> a);
        // when
        usedArgument = mock.functionAR("a");
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
}
