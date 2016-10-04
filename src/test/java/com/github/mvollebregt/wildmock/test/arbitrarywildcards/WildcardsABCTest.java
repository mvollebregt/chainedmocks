package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsABCTest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArguments;

    @Test
    public void testWhenABC() {
        // given
        whenVoid(mock::functionABC, Object.class, Object.class, Object.class).
                then((a, b, c) -> usedArguments = Arrays.asList(a, b, c));
        // when
        mock.functionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }

    @Test
    public void testWhenABCR() {
        // given
        when(mock::functionABCR, Object.class, Object.class, Object.class).then((a, b, c) -> Arrays.asList(a, b, c));
        // when
        usedArguments = mock.functionABCR("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }

    @Test
    public void testVerifyABC() {
        // when
        mock.functionABC("a", "b", "c");
        // then
        verify(mock::functionABC, Object.class, Object.class, Object.class);
    }
}
