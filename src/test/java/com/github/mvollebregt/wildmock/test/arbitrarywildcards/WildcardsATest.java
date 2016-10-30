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

package com.github.mvollebregt.wildmock.test.arbitrarywildcards;

import com.github.mvollebregt.wildmock.test.helpers.ArbitraryWildcardsMockClass;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsATest {

    private final ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArgument;
    private Object returnValue;

    @Test
    public void testVerifyA() {
        // when
        mock.actionA("a");
        // then
        verify(mock::actionA, Object.class);
    }

    @Test
    public void testVerifyWithA() {
        // when
        mock.actionA("a");
        // then
        verify(mock::actionA, String.class).with(a -> a.equals("a"));
    }

    @Test
    public void testWhenA() {
        // given
        when(mock::functionA, Object.class).then(a -> a);
        // when
        usedArgument = mock.functionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testWhenAWithoutWildcards() {
        // given
        when(mock::functionA, Object.class).then(() -> 3);
        // when
        returnValue = mock.functionA("a");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testWhenWithA() {
        // given
        when(mock::functionA, Object.class).with(a -> a.equals("a")).then(a -> a);
        // when
        usedArgument = mock.functionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testTriggerA() {
        // given
        trigger(mock::actionA, Object.class).then(a -> usedArgument = a);
        // when
        mock.actionA("a");
        // then
        assertEquals("a", usedArgument);
    }

    @Test
    public void testTriggerAWithoutWildcards() {
        // given
        trigger(mock::actionA, Object.class).then(() -> returnValue = 3);
        // when
        mock.actionA("a");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testTriggerWithA() {
        // given
        trigger(mock::actionA, Object.class).with(a -> a.equals("a")).then(a -> usedArgument = a);
        // when
        mock.actionA("a");
        // then
        assertEquals("a", usedArgument);
    }
}
