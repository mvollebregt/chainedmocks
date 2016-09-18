package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.testhelpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.chainedmocks.ChainedMocks.mock;
import static com.github.mvollebregt.chainedmocks.ChainedMocks.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class StubBehaviourTest {

    private String status = "mock not called";
    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVoid_MockCalled() {
        // given
        when(mock::action).then(() -> status = "mock called");
        // when
        mock.action();
        // then
        assertEquals("mock called", status);
    }

    @Test
    public void testVoid_MockNotCalled() {
        // given
        when(mock::action).then(() -> status = "mock called");
        // then
        assertEquals("mock not called", status);
    }

    @Test
    public void testVoid_MockCalledTwice() {
        // given
        status = "";
        when(mock::action).then(() -> status += "mock called; ");
        // when
        mock.action();
        mock.action();
        // then
        assertEquals("mock called; mock called; ", status);
    }


    @Test
    public void testTwoVoids_BothCalled() {
        // given
        when(mock::action).then(() -> status = "first action called");
        when(mock::otherAction).then(() -> status += " and second action called");
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
        when(mock::action).then(() -> status = "action on first mock called");
        when(secondMock::action).then(() -> status += " and action on second mock called");
        // when
        mock.action();
        secondMock.action();
        // then
        assertEquals("action on first mock called and action on second mock called", status);
    }

    @Test
    public void testVoidSequence_SequenceCalled() {
        // given
        when(() -> {
            mock.action();
            mock.otherAction();
        }).then(() -> status = "sequence called");
        // when
        mock.action();
        mock.otherAction();
        // then
        assertEquals("sequence called", status);
    }

    @Test
    public void testVoidSequence_OnlyOneCalled() {
        // given
        when(() -> {
            mock.action();
            mock.otherAction();
        }).then(() -> status = "sequence called");
        // when
        mock.otherAction();
        // then
        assertEquals("mock not called", status);
    }

    @Test
    public void testVoidSequence_SequenceCalledWithIntermezzo() {
        // given
        when(() -> {
            mock.action();
            mock.otherAction();
        }).then(() -> status = "sequence called");
        // when
        mock.action();
        mock.yetAnotherAction();
        mock.otherAction();
        // then
        assertEquals("sequence called", status);
    }

    @Test
    public void testVoidSequence_SameSequenceMatchedTwice() {
        // given
        when(() -> {
            mock.action();
            mock.otherAction();
        }).then(() -> status = "sequence called");
        // when
        mock.action();
        mock.action();
        mock.otherAction();
        // then
        assertEquals("sequence called", status);

    }

    @Test
    public void testVoidSequence_TwoSequencesMatched() {
        // given
        status = "";
        when(() -> {
            mock.action();
            mock.otherAction();
        }).then(() -> status += "first action called");
        when(() -> {
            mock.action();
            mock.yetAnotherAction();
        }).then(() -> status += " and second action called");
        // when
        mock.action();
        mock.otherAction();
        mock.yetAnotherAction();
        // then
        assertEquals("first action called and second action called", status);
    }

    @Test
    public void testVoidSequence_ConflictingMatches() {
        // given
        when(() -> {
            mock.otherAction();
            mock.action();
        }).then(() -> {
        });
        when(() -> {
            mock.yetAnotherAction();
            mock.action();
        }).then(() -> {
        });
        // when
        mock.otherAction();
        mock.yetAnotherAction();
        // then
        expectThrows(AmbiguousExpectationsException.class, mock::action);
    }

    @Test
    public void testVoidSequence_MatchedTwice() {
        // given
        status = "";
        when(() -> {
            mock.action();
            mock.otherAction();
        }).then(() -> status += "action called; ");
        // when
        mock.action();
        mock.action();
        mock.otherAction();
        mock.otherAction();
        // then
        assertEquals("action called; action called; ", status);
    }
}
