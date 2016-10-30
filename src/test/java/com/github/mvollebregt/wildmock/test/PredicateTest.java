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

package com.github.mvollebregt.wildmock.test;

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PredicateTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    private Object usedArgument;

    @Test
    public void testStubWithPredicate_success() {
        // given
        trigger(mock::consume, String.class).with(a -> a.equals("a")).then(a -> usedArgument = a);
        // when
        mock.consume("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testStubWithPredicate_fail() {
        // given
        trigger(mock::consume, String.class).with(a -> a.equals("a")).then(a -> usedArgument = a);
        // when
        mock.consume("other value");
        // then
        assertEquals(null, usedArgument);
    }

    @Test
    public void testStubWithTwoPredicates() {
        // given
        trigger(mock::consume, String.class).with(a -> a.equals("a")).with(a -> false).then(a -> usedArgument = a);
        // when
        mock.consume("a");
        // then
        assertEquals(null, usedArgument);

    }

    @Test
    public void testVerifyPredicate_success() {
        // when
        mock.consume("a");
        // then
        verify(mock::consume, String.class).with(a -> a.equals("a"));
    }

    @Test
    public void testVerifyPredicate_fail() {
        // when
        mock.consume("other value");
        // then
        assertThrows(VerificationException.class, () -> verify(mock::consume, String.class).with(a -> a.equals("a")));
    }
}