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

import com.github.mvollebregt.wildmock.function.ActionA;
import com.github.mvollebregt.wildmock.function.FunctionA;

public class VerifyA<A> extends Verify {

    public static <A> VerifyA<A> verify(ActionA<A> expectedCalls, Class<A> a) {
        VerifyA<A> verify = new VerifyA<>(expectedCalls, a);
        verify.check();
        return verify;
    }

    public void with(FunctionA<A, Boolean> predicate) {
        super.with(predicate);
    }

    private VerifyA(ActionA<A> from, Class<A> a) {
        super(from, a);
    }
}