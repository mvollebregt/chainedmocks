package com.github.mvollebregt.wildmock.test;

import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArbitraryParametersTest {

    private ClassToBeMocked mock = mock(ClassToBeMocked.class);

    private List<Object> usedArguments;

    @Test
    public void testWhenABC() {
        // given
        when(mock::functionABC, Object.class, Object.class, Object.class).
                then((a, b, c) -> usedArguments = Arrays.asList(a, b, c));
        // when
        mock.functionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }


}
