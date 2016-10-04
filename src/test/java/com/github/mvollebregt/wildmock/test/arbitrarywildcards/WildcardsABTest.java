package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import com.github.mvollebregt.wildmock.Wildmock;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsABTest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArguments;

    @Test
    public void testWhenAB() {
        // given
        Wildmock.whenVoid(mock::functionAB, Object.class, Object.class).
                then((a, b) -> usedArguments = Arrays.asList(a, b));
        // when
        mock.functionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }

    @Test
    public void testWhenABR() {
        // given
        when(mock::functionABR, Object.class, Object.class).then((a, b) -> Arrays.asList(a, b));
        // when
        usedArguments = mock.functionABR("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }

    @Test
    public void testVerifyAB() {
        // when
        mock.functionAB("a", "b");
        // then
        verify(mock::functionAB, Object.class, Object.class);
    }
}
