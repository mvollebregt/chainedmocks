package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.mock;
import static com.github.mvollebregt.chainedmocks.ChainedMocks.verify;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class VerifyTest {

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
    public void testVerify_MockReturningObject() {
        // when
        mock.provide(String.class);
        // then
        verify(() -> mock.provide(String.class));
    }

    @Test
    public void testVerify_MockReturningPrimitive() {
        // when
        mock.provideInt();
        // then
        verify(mock::provideInt);
    }
}
