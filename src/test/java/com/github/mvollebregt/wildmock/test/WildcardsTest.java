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

import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.trigger;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class WildcardsTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);
    private String status = "mock not called";

    @Test
    public void testStubOneWildcard() {
        // given
        trigger(mock::consume, String.class).then(wildcard -> status = "mock called with wildcard " + wildcard);
        // when
        mock.consume("argument");
        // then
        assertEquals("mock called with wildcard argument", status);
    }

    @Test
    public void testStubPrimitiveWildcard() {
        // given
        trigger(mock::consume, Integer.class).then(wildcard -> status = "mock called with wildcard " + wildcard);
        // when
        mock.consume(3);
        // then
        assertEquals("mock called with wildcard 3", status);
    }

    @Test
    public void testWildcardUsedTwice_Success() {
        trigger(wildcard -> {
            mock.consume(wildcard);
            mock.consume(wildcard);
        }, String.class).then(wildcard -> status = "call sequence called with wildcard " + wildcard);
        // when
        mock.consume("argument");
        mock.consume("argument");
        // then
        assertEquals("call sequence called with wildcard argument", status);
    }

    @Test
    public void testWildcardUsedTwice_Failure() {
        trigger(wildcard -> {
            mock.consume(wildcard);
            mock.consume(wildcard);
        }, String.class).then(wildcard -> status = "call sequence called with wildcard " + wildcard);
        // when
        mock.consume("argument");
        mock.consume("argument");
        // then
        assertEquals("call sequence called with wildcard argument", status);
    }

    @Test
    public void testDifferentReturnValue() {
        // given
        trigger((wildcard) -> {
            mock.action();
            int value = mock.identityFunction(wildcard);
            mock.consume(value);
        }, Integer.class).then(() -> status = "call sequence called");
        // when
        mock.action();
        mock.identityFunction(1); // first call is matched, "1" is taken as wildcard
        int secondReturnValue = mock.identityFunction(2); // however, "2" is the actual wildcard
        mock.consume(secondReturnValue);
        // then
        assertEquals("call sequence called", status);
    }
}
