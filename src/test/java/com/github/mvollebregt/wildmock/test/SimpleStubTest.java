package com.github.mvollebregt.wildmock.test;

import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.whenVoid;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleStubTest {

    private String status = "mock not called";
    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVoid_MockCalled() {
        // given
        whenVoid(mock::action).then(() -> status = "mock called");
        // when
        mock.action();
        // then
        assertEquals("mock called", status);
    }

    @Test
    public void testVoid_MockNotCalled() {
        // given
        whenVoid(mock::action).then(() -> status = "mock called");
        // then
        assertEquals("mock not called", status);
    }

    @Test
    public void testVoid_MockCalledTwice() {
        // given
        status = "";
        whenVoid(mock::action).then(() -> status += "mock called; ");
        // when
        mock.action();
        mock.action();
        // then
        assertEquals("mock called; mock called; ", status);
    }


    @Test
    public void testTwoVoids_BothCalled() {
        // given
        whenVoid(mock::action).then(() -> status = "first action called");
        whenVoid(mock::otherAction).then(() -> status += " and second action called");
        // when
        mock.action();
        mock.otherAction();
        // then
        assertEquals("first action called and second action called", status);
    }

    @Test
    public void testVoidOnTwoMocks_BothCalled() {
        // given
        ClassToBeMocked secondMock = mock(ClassToBeMocked.class);
        whenVoid(mock::action).then(() -> status = "action on first mock called");
        whenVoid(secondMock::action).then(() -> status += " and action on second mock called");
        // when
        mock.action();
        secondMock.action();
        // then
        assertEquals("action on first mock called and action on second mock called", status);
    }

    @Test
    public void testFunctionA_MockCalled() {
        // given
        whenVoid(() -> mock.consume("expected value")).then(() -> status = "mock called");
        // when
        mock.consume("expected value");
        // then
        assertEquals("mock called", status);
    }

    @Test
    public void testFunctionA_MockNotCalled() {
        // given
        whenVoid(() -> mock.consume("expected value")).then(() -> status = "mock called");
        // when
        mock.consume("other value");
        // then
        assertEquals("mock not called", status);
    }
}
