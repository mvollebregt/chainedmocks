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

package com.github.mvollebregt.wildmock.function;

/**
 * Generic interface for something that can be called, with arbitrary parameters, and that may or may not return a
 * value.
 *
 * @param <R> The type of the returned value, or {@link Void} if the apply function is not meant to return anything.
 */
public interface VarargsCallable<R> {

    /**
     * Executes with the specified arguments.
     *
     * @param arguments a list of arguments.
     * @return a value, or null if the function is not meant to return anything.
     */
    R apply(Object... arguments);

    /**
     * Indicates if the apply function returns a value.
     *
     * @return true if the apply function returns a value, false if it is not meant to return anything.
     */
    boolean returnsValue();

}
