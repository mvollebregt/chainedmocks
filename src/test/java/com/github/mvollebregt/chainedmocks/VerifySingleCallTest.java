package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.mock;
import static com.github.mvollebregt.chainedmocks.ChainedMocks.verify;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class VerifySingleCallTest {

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
