package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.mock;
import static com.github.mvollebregt.chainedmocks.ChainedMocks.verify;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class VerifyChainedCallsTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVerifyIntPassing_Success() {
        // when
        int passingInt = mock.provideInt();
        mock.consume(passingInt);
        // then
        verify(() -> {
            int verifiedInt = mock.provideInt();
            mock.consume(verifiedInt);
        });
    }

    @Test
    public void testVerifyIntPassing_Failure() {
        // when
        mock.provideInt();
        mock.consume(0);
        // then
        expectThrows(VerificationException.class, () -> verify(() -> {
            int verifiedInt = mock.provideInt();
            mock.consume(verifiedInt);
        }));
    }
}
