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

import java.util.Arrays;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WildcardsABCTest {

    private final ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArguments;
    private Object returnValue;

    @Test
    public void testVerifyABC() {
        // when
        mock.actionABC("a", "b", "c");
        // then
        verify(mock::actionABC, Object.class, Object.class, Object.class);
    }

    @Test
    public void testVerifyWithABC() {
        // when
        mock.actionABC("a", "b", "c");
        // then
        verify(mock::actionABC, Object.class, Object.class, Object.class).
                with((a, b, c) -> a.equals("a") && b.equals("b") && c.equals("c"));
    }

    @Test
    public void testWhenABC() {
        // given
        when(mock::functionABC, Object.class, Object.class, Object.class).then((a, b, c) -> Arrays.asList(a, b, c));
        // when
        usedArguments = mock.functionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }

    @Test
    public void testWhenABCWithoutWildcards() {
        // given
        when(mock::functionABC, Object.class, Object.class, Object.class).then(() -> 3);
        // when
        returnValue = mock.functionABC("a", "b", "c");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testWhenWithABC() {
        // given
        when(mock::functionABC, Object.class, Object.class, Object.class).
                with((a, b, c) -> a.equals("a") && b.equals("b") && c.equals("c")).
                then((a, b, c) -> Arrays.asList(a, b, c));
        // when
        usedArguments = mock.functionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }

    @Test
    public void testTriggerABC() {
        // given
        trigger(mock::actionABC, Object.class, Object.class, Object.class).
                then((a, b, c) -> usedArguments = Arrays.asList(a, b, c));
        // when
        mock.actionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }

    @Test
    public void testTriggerABCWithoutWildcards() {
        // given
        trigger(mock::actionABC, Object.class, Object.class, Object.class).then(() -> returnValue = 3);
        // when
        mock.actionABC("a", "b", "c");
        // then
        assertEquals(3, returnValue);
    }


    @Test
    public void testTriggerWithABC() {
        // given
        trigger(mock::actionABC, Object.class, Object.class, Object.class).
                with((a, b, c) -> a.equals("a") && b.equals("b") && c.equals("c")).
                then((a, b, c) -> usedArguments = Arrays.asList(a, b, c));
        // when
        mock.actionABC("a", "b", "c");
        // then
        assertEquals(Arrays.asList("a", "b", "c"), usedArguments);
    }
}
