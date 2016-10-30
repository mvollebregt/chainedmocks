/*
 * Copyright 2016 Michel Vollebregt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mvollebregt.wildmock.test.feedback;

import com.github.mvollebregt.wildmock.exceptions.VerifyClauseNotSatisfiedException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.verify;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * Tests that show that in the verification feedback to the programmer, wildcards are represented in a way that is
 * clearly recognisable.
 */
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class VerifyNotSatisfiedWildcardsTest {

    private ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testInputWildcard() {
        // when: call with input wildcard is verified and not satisfied
        VerifyClauseNotSatisfiedException exception = expectThrows(VerifyClauseNotSatisfiedException.class, () ->
                verify(mock::consume, String.class));
        System.out.println(exception.getMessage());
        // then: the method argument is represented with a useful description with the type of the input wildcard
        String stringRepresentation = exception.getClosestMatch().toString();
        assertTrue(stringRepresentation.contains("a String"));
    }

    @Test
    public void testOutputWildcard() {
        // when: call with output wildcard is verified and not satisfied
        VerifyClauseNotSatisfiedException exception = expectThrows(VerifyClauseNotSatisfiedException.class, () ->
                verify(() -> {
                    String outputWildcard = mock.provideString();
                    mock.consume(outputWildcard);
                }));
        // then: the method argument is represented with the useful description "output of 1"
        System.out.println(exception.getMessage());
        String stringRepresentation = exception.getClosestMatch().toString();
        assertTrue(stringRepresentation.contains("output of 1"));
    }
}
