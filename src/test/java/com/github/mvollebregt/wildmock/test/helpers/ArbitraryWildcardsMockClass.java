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

package com.github.mvollebregt.wildmock.test.helpers;

import static org.junit.jupiter.api.Assertions.fail;

public class ArbitraryWildcardsMockClass {

    public void actionA(Object a) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionA(Object a) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public void actionAB(Object a, Object b) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionAB(Object a, Object b) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public void actionABC(Object a, Object b, Object c) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public Object functionABC(Object a, Object b, Object c) {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

}
