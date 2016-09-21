package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.mock;
import static com.github.mvollebregt.chainedmocks.ChainedMocks.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StubReturningValueTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testStubReturningObject() {
        // given
        when(() -> mock.provide(String.class)).then(() -> "expected value");
        // when
        String value = mock.provide(String.class);
        // then
        assertEquals("expected value", value);
    }

    @Test
    public void testStubReturningByte() {
        // given
        when(mock::provideByte).then(() -> (byte) 3);
        // when
        byte value = mock.provideByte();
        // then
        assertEquals(3, value);
    }

    @Test
    public void testStubReturningShort() {
        // given
        when(mock::provideShort).then(() -> (short) 3);
        // when
        short value = mock.provideShort();
        // then
        assertEquals(3, value);
    }

    @Test
    public void testStubReturningInt() {
        // given
        when(mock::provideInt).then(() -> 3);
        // when
        int value = mock.provideInt();
        // then
        assertEquals(3, value);
    }

    @Test
    public void testStubReturningLong() {
        // given
        when(mock::provideLong).then(() -> 3L);
        // when
        long value = mock.provideLong();
        // then
        assertEquals(3L, value);
    }

    @Test
    public void testStubReturningFloat() {
        // given
        when(mock::provideFloat).then(() -> 3F);
        // when
        float value = mock.provideFloat();
        // then
        assertEquals(3F, value);
    }

    @Test
    public void testStubReturningDouble() {
        // given
        when(mock::provideDouble).then(() -> 3D);
        // when
        double value = mock.provideDouble();
        // then
        assertEquals(3D, value);
    }

    @Test
    public void testStubReturningBoolean() {
        // given
        when(mock::provideBoolean).then(() -> true);
        // when
        boolean value = mock.provideBoolean();
        // then
        assertEquals(true, value);
    }

    @Test
    public void testStubReturningChar() {
        // given
        when(mock::provideChar).then(() -> '3');
        // when
        char value = mock.provideChar();
        // then
        assertEquals('3', value);
    }
}
