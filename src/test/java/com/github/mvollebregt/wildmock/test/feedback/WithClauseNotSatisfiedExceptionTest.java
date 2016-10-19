package com.github.mvollebregt.wildmock.test.feedback;

import com.github.mvollebregt.wildmock.exceptions.WithClauseNotSatisfiedException;
import com.github.mvollebregt.wildmock.test.helpers.ArbitraryWildcardsMockClass;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.verify;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class WithClauseNotSatisfiedExceptionTest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    @Test
    public void testOneMatchWithClauseNotSatisfied() {
        // when
        mock.actionA("unexpected input");
        // then
        WithClauseNotSatisfiedException exception = expectThrows(WithClauseNotSatisfiedException.class, () ->
                verify(mock::actionA, String.class).with(wildcard -> wildcard.equals("expected input"))
        );
        assertTrue(exception.getMessage().contains("unexpected input"));
        assertEquals(singletonList(singletonList("unexpected input")),
                exception.getObservedWildcards().stream().map(Arrays::asList).collect(toList()));
    }

    @Test
    public void testTwoMatchesWithClauseNotSatisfied() {
        // when
        mock.actionA("first unexpected input");
        mock.actionA("second unexpected input");
        // then
        WithClauseNotSatisfiedException exception = expectThrows(WithClauseNotSatisfiedException.class, () ->
                verify(mock::actionA, String.class).with(wildcard -> wildcard.equals("expected input"))
        );
        String message = exception.getMessage();
        assertTrue(message.contains("first unexpected input") && message.contains("second unexpected input") &&
                message.contains("2 times"));
        assertEquals(asList(singletonList("first unexpected input"), singletonList("second unexpected input")),
                exception.getObservedWildcards().stream().map(Arrays::asList).collect(toList()));
    }

    @Test
    public void testTwoWildcardsWithClauseNotSatisfied() {
        // when
        mock.actionAB("first unexpected input", "second unexpected input");
        // then
        WithClauseNotSatisfiedException exception = expectThrows(WithClauseNotSatisfiedException.class, () ->
                verify(mock::actionAB, String.class, String.class).
                        with((firstParameter, secondParameter) -> firstParameter.equals("expected input"))
        );
        assertTrue(exception.getMessage().contains("first unexpected input, second unexpected input"));
        assertEquals(singletonList(asList("first unexpected input", "second unexpected input")),
                exception.getObservedWildcards().stream().map(Arrays::asList).collect(toList()));
    }
}
