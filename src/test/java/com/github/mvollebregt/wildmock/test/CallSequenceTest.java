package com.github.mvollebregt.wildmock.test;

import com.github.mvollebregt.wildmock.exceptions.AmbiguousExpectationsException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class CallSequenceTest {

    private String status = "mock not called";
    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVoidSequence_SequenceCalled() {
        // given
        trigger(() -> {
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
        trigger(() -> {
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
        trigger(() -> {
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
        trigger(() -> {
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
        trigger(() -> {
            mock.action();
            mock.otherAction();
        }).then(() -> status += "first action called");
        trigger(() -> {
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
    public void testVoidSequence_DoubleButNonConflictingMatch() {
        // given
        trigger(() -> {
            mock.otherAction();
            mock.action();
        }).then(() -> status = "first action called ");
        trigger(() -> {
            mock.yetAnotherAction();
            mock.action();
        }).then(() -> status += "and second action called");
        // when
        mock.otherAction();
        mock.yetAnotherAction();
        mock.action();
        // then
        assertEquals("first action called and second action called", status);
    }


    @Test
    public void testVoidSequence_ConflictingMatches() {
        // given
        when(() -> {
            mock.otherAction();
            return mock.provideInt();
        }).then(() -> 2);
        when(() -> {
            mock.yetAnotherAction();
            return mock.provideInt();
        }).then(() -> 3);
        // when
        mock.otherAction();
        mock.yetAnotherAction();
        // then
        expectThrows(AmbiguousExpectationsException.class, mock::provideInt);
    }

    @Test
    public void testVoidSequence_MatchedTwice() {
        // given
        status = "";
        trigger(() -> {
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
