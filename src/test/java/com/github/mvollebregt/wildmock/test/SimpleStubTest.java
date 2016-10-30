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

public class SimpleStubTest {

    private String status = "mock not called";
    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVoid_MockCalled() {
        // given
        trigger(mock::action).then(() -> status = "mock called");
        // when
        mock.action();
        // then
        assertEquals("mock called", status);
    }

    @Test
    public void testVoid_MockNotCalled() {
        // given
        trigger(mock::action).then(() -> status = "mock called");
        // then
        assertEquals("mock not called", status);
    }

    @Test
    public void testVoid_MockCalledTwice() {
        // given
        status = "";
        trigger(mock::action).then(() -> status += "mock called; ");
        // when
        mock.action();
        mock.action();
        // then
        assertEquals("mock called; mock called; ", status);
    }


    @Test
    public void testTwoVoids_BothCalled() {
        // given
        trigger(mock::action).then(() -> status = "first action called");
        trigger(mock::otherAction).then(() -> status += " and second action called");
        // when
        mock.action();
        mock.otherAction();
        // then
        assertEquals("first action called and second action called", status);
    }

    @Test
    public void testVoidOnTwoMocks_BothCalled() {
        // given
        ClassToBeMocked secondMock = mock(ClassToBeMocked.class);
        trigger(mock::action).then(() -> status = "action on first mock called");
        trigger(secondMock::action).then(() -> status += " and action on second mock called");
        // when
        mock.action();
        secondMock.action();
        // then
        assertEquals("action on first mock called and action on second mock called", status);
    }

    @Test
    public void testFunctionA_MockCalled() {
        // given
        trigger(() -> mock.consume("expected value")).then(() -> status = "mock called");
        // when
        mock.consume("expected value");
        // then
        assertEquals("mock called", status);
    }

    @Test
    public void testFunctionA_MockNotCalled() {
        // given
        trigger(() -> mock.consume("expected value")).then(() -> status = "mock called");
        // when
        mock.consume("other value");
        // then
        assertEquals("mock not called", status);
    }
}
