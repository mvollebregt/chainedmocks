package com.github.mvollebregt.wildmock.test.feedback;

import com.github.mvollebregt.wildmock.api.MethodCall;
import com.github.mvollebregt.wildmock.exceptions.VerifyClauseNotSatisfiedException;
import com.github.mvollebregt.wildmock.test.helpers.ArbitraryWildcardsMockClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.verify;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class VerifyClauseNotSatisfiedExceptionTest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    @Test
    public void testPartialMatch() throws Exception {
        // when
        mock.actionA("first call");
        VerifyClauseNotSatisfiedException exception = expectThrows(VerifyClauseNotSatisfiedException.class, () ->
                verify(() -> {
                    mock.actionA("first call");
                    mock.actionA("second call");
                }));
        System.out.println(exception.getMessage());
        assertEquals(singletonList(actionA("first call")), exception.getClosestMatch().getObservedMethodCalls());
        assertNull(exception.getClosestMatch().getMismatchedMethodCall());
        assertEquals(singletonList(actionA("second call")), exception.getClosestMatch().getRemainingMethodCalls());
    }

    @Test
    public void testWrongArgument() throws Exception {
        // when
        mock.actionA("unexpected argument");
        VerifyClauseNotSatisfiedException exception = expectThrows(VerifyClauseNotSatisfiedException.class, () ->
                verify(() -> mock.actionA("expected argument")));
        System.out.println(exception.getMessage());
        assertEquals(actionA("unexpected argument"), exception.getClosestMatch().getMismatchedMethodCall());
        assertEquals(singletonList(actionA("expected argument")), exception.getClosestMatch().getRemainingMethodCalls());
    }

    @Test
    public void testWrongMethod() throws Exception {
        // when
        mock.actionAB(null, null);
        VerifyClauseNotSatisfiedException exception = expectThrows(VerifyClauseNotSatisfiedException.class, () ->
                verify(() -> mock.actionA("expected argument")));
        System.out.println(exception.getMessage());
        assertEquals(actionAB(null, null), exception.getClosestMatch().getMismatchedMethodCall());
    }

    @Test
    public void testWrongArgumentTwice() throws Exception {
        // when: action is called with wrong argument twice
        mock.actionA("unexpected 1");
        mock.actionA("unexpected 2");
        VerifyClauseNotSatisfiedException exception = expectThrows(VerifyClauseNotSatisfiedException.class, () ->
                verify(() -> mock.actionA("expected argument")));
        // then: the first argument is registered as closest match
        assertEquals(actionA("unexpected 1"), exception.getClosestMatch().getMismatchedMethodCall());
    }


    @Test
    public void testWrongMethodThenWrongArgument() throws Exception {
        // when: action is called with wrong method and then with wrong argument
        mock.actionAB(null, null);
        mock.actionA("unexpected argument");
        VerifyClauseNotSatisfiedException exception = expectThrows(VerifyClauseNotSatisfiedException.class, () ->
                verify(() -> mock.actionA("expected argument")));
        // then: the closest call is registered as closest match
        assertEquals(actionA("unexpected argument"), exception.getClosestMatch().getMismatchedMethodCall());
    }

    private MethodCall actionA(String argument) throws Exception {
        Method actionA = ArbitraryWildcardsMockClass.class.getMethod("actionA", Object.class);
        return new MethodCall(mock, actionA, new Object[]{argument}, null);
    }

    private MethodCall actionAB(String arg1, String arg2) throws Exception {
        Method actionAB = ArbitraryWildcardsMockClass.class.getMethod("actionAB", Object.class, Object.class);
        return new MethodCall(mock, actionAB, new Object[]{arg1, arg2}, null);
    }
}
