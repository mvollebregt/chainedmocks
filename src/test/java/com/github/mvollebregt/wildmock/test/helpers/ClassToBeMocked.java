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

@SuppressWarnings({"SameReturnValue", "UnusedParameters"})
public class ClassToBeMocked {

    public void action() {
        fail("Underlying methods on mocked class should not be called.");
    }

    public void otherAction() {
        fail("Underlying methods on mocked class should not be called.");
    }

    public void yetAnotherAction() {
        fail("Underlying methods on mocked class should not be called.");
    }

    public void consume(String argument) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public void consume(int passingInt) {
        fail("Underlying methods on mocked class should not be called.");
    }

    public String provideString() {
        fail("Underlying methods on mocked class should not be called.");
        return null;
    }

    public byte provideByte() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }


    public short provideShort() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public int provideInt() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public long provideLong() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public float provideFloat() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public double provideDouble() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public boolean provideBoolean() {
        fail("Underlying methods on mocked class should not be called.");
        return false;
    }

    public char provideChar() {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }

    public int identityFunction(int i) {
        fail("Underlying methods on mocked class should not be called.");
        return 0;
    }
}
