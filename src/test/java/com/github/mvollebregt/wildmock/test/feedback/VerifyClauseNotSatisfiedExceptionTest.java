package com.github.mvollebregt.wildmock.test.feedback;

import com.github.mvollebregt.wildmock.api.MethodCall;
import com.github.mvollebregt.wildmock.exceptions.VerifyClauseNotSatisfiedException;
import com.github.mvollebregt.wildmock.test.helpers.ArbitraryWildcardsMockClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.verify;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class VerifyClauseNotSatisfiedExceptionTest {

    private ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void testPartialMatch() throws Exception {
        // when
        mock.actionA("first call");
        VerifyClauseNotSatisfiedException exception = expectThrows(VerifyClauseNotSatisfiedException.class, () ->
                verify(() -> {
                    mock.actionA("first call");
                    mock.actionA("second call");
                }));
        assertEquals(singletonList(actionA("first call")), exception.getObservedMethodCalls());
        assertEquals(singletonList(actionA("second call")), exception.getRemainingMethodCalls());
    }

    private MethodCall actionA(String argument) throws Exception {
        Method actionA = ArbitraryWildcardsMockClass.class.getMethod("actionA", Object.class);
        return new MethodCall(mock, actionA, new Object[]{argument}, null);
    }
}
