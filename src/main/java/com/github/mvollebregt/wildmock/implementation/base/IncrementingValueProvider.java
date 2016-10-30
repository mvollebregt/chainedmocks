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

package com.github.mvollebregt.wildmock.implementation.base;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

public class IncrementingValueProvider {

    private static final long INITIAL_SEED = 19760713;

    private final Objenesis objenesis = new ObjenesisStd();

    private long seed;

    public IncrementingValueProvider() {
        this.seed = INITIAL_SEED;
    }

    public Object provide(Class type) {
        seed += 27;
        if (type.equals(Byte.TYPE)) {
            return (byte) seed;
        } else if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            return (short) seed;
        } else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return (int) seed;
        } else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return seed;
        } else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            return (float) seed;
        } else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
            return (double) seed;
        } else if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
            return seed % 2 == 0;
        } else if (type.equals(Character.TYPE) || type.equals(Character.class)) {
            return (char) seed;
        } else if (type.equals(String.class)) {
            return String.valueOf(seed);
        } else if (type.equals(Void.TYPE)) {
            return null;
        } else {
            return objenesis.newInstance(type);
        }
    }
}
