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

import com.github.mvollebregt.wildmock.function.ActionAB;
import com.github.mvollebregt.wildmock.function.FunctionAB;

public class VerifyAB<A, B> extends Verify {

    public static <A, B> VerifyAB<A, B> verify(ActionAB<A, B> expectedCalls, Class<A> a, Class<B> b) {
        VerifyAB<A, B> verify = new VerifyAB<>(expectedCalls, a, b);
        verify.check();
        return verify;
    }

    public void with(FunctionAB<A, B, Boolean> predicate) {
        super.with(predicate);
    }

    private VerifyAB(ActionAB<A, B> from, Class<A> a, Class<B> b) {
        super(from, a, b);
    }
}