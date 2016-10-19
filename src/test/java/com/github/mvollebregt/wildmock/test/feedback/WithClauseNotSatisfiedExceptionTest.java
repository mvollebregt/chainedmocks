package com.github.mvollebregt.wildmock.test.feedback;

import com.github.mvollebregt.wildmock.exceptions.WithClauseNotSatisfiedException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
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

    private ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testOneMatchWithClauseNotSatisfied() {
        // when
        mock.consume("unexpected input");
        // then
        WithClauseNotSatisfiedException exception = expectThrows(WithClauseNotSatisfiedException.class, () ->
                verify(mock::consume, String.class).with(wildcard -> wildcard.equals("expected input"))
        );
        assertTrue(exception.getMessage().contains("unexpected input"));
        assertEquals(singletonList(singletonList("unexpected input")),
                exception.getObservedWildcards().stream().map(Arrays::asList).collect(toList()));
    }

    @Test
    public void testTwoMatchesWithClauseNotSatisfied() {
        // when
        mock.consume("first unexpected input");
        mock.consume("second unexpected input");
        // then
        WithClauseNotSatisfiedException exception = expectThrows(WithClauseNotSatisfiedException.class, () ->
                verify(mock::consume, String.class).with(wildcard -> wildcard.equals("expected input"))
        );
        String message = exception.getMessage();
        assertTrue(message.contains("first unexpected input") && message.contains("second unexpected input") &&
                message.contains("2 times"));
        assertEquals(asList(singletonList("first unexpected input"), singletonList("second unexpected input")),
                exception.getObservedWildcards().stream().map(Arrays::asList).collect(toList()));
    }
}
