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

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.verify;
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class SimpleVerifyTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVerify_MockCalled() {
        // when
        mock.action();
        // then
        verify(mock::action);
    }

    @Test
    public void testVerify_MockNotCalled() {
        expectThrows(VerificationException.class, () -> verify(mock::action));
    }

    @Test
    public void testVerifyFunctionA_Called() {
        // when
        mock.consume("expected value");
        // then
        verify(() -> mock.consume("expected value"));
    }

    @Test
    public void testVerifyFunctionA_NotCalled() {
        // when
        mock.consume("unexpected value");
        //
        expectThrows(VerificationException.class, () -> verify(() -> mock.consume("expected value")));
    }

    @Test
    public void testVerify_MockReturningObject() {
        // when
        mock.provideString();
        // then
        verify(mock::provideString);
    }

    @Test
    public void testVerify_MockReturningPrimitive() {
        // when
        mock.provideInt();
        // then
        verify(mock::provideInt);
    }
}
