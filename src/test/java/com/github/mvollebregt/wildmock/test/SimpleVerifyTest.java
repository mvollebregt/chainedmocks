package com.github.mvollebregt.wildmock.test;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.verify;
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class SimpleVerifyTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVerify_MockCalled() {
        // when
        mock.action();
        // then
        verify(mock::action);
    }

    @Test
    public void testVerify_MockNotCalled() {
        expectThrows(VerificationException.class, () -> verify(mock::action));
    }

    @Test
    public void testVerifyConsumer_Called() {
        // when
        mock.consume("expected value");
        // then
        verify(() -> mock.consume("expected value"));
    }

    @Test
    public void testVerifyConsumer_NotCalled() {
        // when
        mock.consume("unexpected value");
        //
        expectThrows(VerificationException.class, () -> verify(() -> mock.consume("expected value")));
    }

    @Test
    public void testVerify_MockReturningObject() {
        // when
        mock.provideString();
        // then
        verify(mock::provideString);
    }

    @Test
    public void testVerify_MockReturningPrimitive() {
        // when
        mock.provideInt();
        // then
        verify(mock::provideInt);
    }
}
