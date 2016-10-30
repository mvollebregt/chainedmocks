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
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class CallSequencePassingValuesTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);
    private String status = "mock not called";

    @Test
    public void testStubStringPassing_Success() {
        // given
        trigger(() -> {
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
        trigger(() -> {
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
