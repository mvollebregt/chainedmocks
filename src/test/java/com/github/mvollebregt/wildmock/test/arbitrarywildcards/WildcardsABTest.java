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

public class WildcardsABTest {

    private final ArbitraryWildcardsMockClass mock = mock(ArbitraryWildcardsMockClass.class);

    private Object usedArguments;
    private Object returnValue;

    @Test
    public void testVerifyAB() {
        // when
        mock.actionAB("a", "b");
        // then
        verify(mock::actionAB, Object.class, Object.class);
    }

    @Test
    public void testVerifyWithAB() {
        // when
        mock.actionAB("a", "b");
        // then
        verify(mock::actionAB, Object.class, Object.class).with((a, b) -> a.equals("a") && b.equals("b"));
    }

    @Test
    public void testWhenAB() {
        // given
        when(mock::functionAB, Object.class, Object.class).then((a, b) -> Arrays.asList(a, b));
        // when
        usedArguments = mock.functionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }

    @Test
    public void testWhenABWithoutWildcards() {
        // given
        when(mock::functionAB, Object.class, Object.class).then(() -> 3);
        // when
        returnValue = mock.functionAB("a", "b");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testWhenWithAB() {
        // given
        when(mock::functionAB, Object.class, Object.class).
                with((a, b) -> a.equals("a") && b.equals("b")).
                then((a, b) -> Arrays.asList(a, b));
        // when
        usedArguments = mock.functionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }

    @Test
    public void testTriggerAB() {
        // given
        trigger(mock::actionAB, Object.class, Object.class).
                then((a, b) -> usedArguments = Arrays.asList(a, b));
        // when
        mock.actionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }

    @Test
    public void testTriggerAWithoutWildcards() {
        // given
        trigger(mock::actionAB, Object.class, Object.class).then(() -> returnValue = 3);
        // when
        mock.actionAB("a", "b");
        // then
        assertEquals(3, returnValue);
    }

    @Test
    public void testTriggerWithAB() {
        // given
        trigger(mock::actionAB, Object.class, Object.class).
                with((a, b) -> a.equals("a") && b.equals("b")).
                then((a, b) -> usedArguments = Arrays.asList(a, b));
        // when
        mock.actionAB("a", "b");
        // then
        assertEquals(Arrays.asList("a", "b"), usedArguments);
    }
}
