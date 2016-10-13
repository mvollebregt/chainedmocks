package com.github.mvollebregt.wildmock.test;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.verify;
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class ConditionalsTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVariableNumberOfCalls() {
        // when
        mock.identityFunction(2);
        // then: this should fail:
        expectThrows(VerificationException.class, () -> verify((wildcard) -> {
            int dummyValue = mock.identityFunction(wildcard);
            if (dummyValue == 2) {
                mock.action();
            }
        }, Integer.class));
    }

    @Test
    public void testWildcardInConditional() {
        // when
        mock.identityFunction(2);
        mock.consume(3);
        // then: this should succeed
        verify((a, b) -> {
            int dummyValue = mock.identityFunction(a);
            if (dummyValue == 2) {
                mock.consume(b);
            }
        }, Integer.class, Integer.class);
    }
}
