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

import com.github.mvollebregt.wildmock.function.ActionABC;
import com.github.mvollebregt.wildmock.function.FunctionABC;

public class VerifyABC<A, B, C> extends Verify {

    public static <A, B, C> VerifyABC<A, B, C> verify(ActionABC<A, B, C> expectedCalls,
                                                      Class<A> a, Class<B> b, Class<C> c) {
        VerifyABC<A, B, C> verify = new VerifyABC<>(expectedCalls, a, b, c);
        verify.check();
        return verify;
    }

    public void with(FunctionABC<A, B, C, Boolean> predicate) {
        super.with(predicate);
    }

    private VerifyABC(ActionABC<A, B, C> from, Class<A> a, Class<B> b, Class<C> c) {
        super(from, a, b, c);
    }
}