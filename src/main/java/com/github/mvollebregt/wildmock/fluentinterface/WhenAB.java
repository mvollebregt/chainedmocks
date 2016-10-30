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

package com.github.mvollebregt.wildmock.fluentinterface;

import com.github.mvollebregt.wildmock.function.FunctionAB;

public class WhenAB<A, B, R> extends When<R> {

    public WhenAB(FunctionAB<A, B, R> expectedCalls, Class<A> a, Class<B> b) {
        super(expectedCalls, a, b);
    }

    public WhenAB<A, B, R> with(FunctionAB<A, B, Boolean> predicate) {
        return new WhenAB<>(this, predicate);
    }

    public void then(FunctionAB<A, B, R> behaviour) {
        super.then(behaviour);
    }

    private WhenAB(WhenAB<A, B, R> source, FunctionAB<A, B, Boolean> predicate) {
        super(source, predicate);
    }
}
