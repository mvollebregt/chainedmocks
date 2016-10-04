package com.github.mvollebregt.wildmock.test;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class CallSequencePassingValuesTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);
    private String status = "mock not called";

    @Test
    public void testStubStringPassing_Success() {
        // given
        whenVoid(() -> {
            String passingString = mock.provideString();
            mock.consume(passingString);
        }).then(() -> status = "mock called");
        // when
        String passingString = mock.provideString();
        mock.consume(passingString);
        // then
        assertEquals("mock called", status);
    }

    @Test
    public void testStubStringPassing_Failure() {
        // given
        whenVoid(() -> {
            String passingString = mock.provideString();
            mock.consume(passingString);
        }).then(() -> status = "mock called");
        // when
        mock.provideString();
        mock.consume("unexpected value");
        // then
        assertEquals("mock not called", status);
    }

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