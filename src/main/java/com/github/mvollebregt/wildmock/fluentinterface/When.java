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

import com.github.mvollebregt.wildmock.function.FunctionX;
import com.github.mvollebregt.wildmock.function.VarargsCallable;
import com.github.mvollebregt.wildmock.function.VarargsFunction;

public class When<R> extends Stub {

    public When(FunctionX<R> expectedCalls) {
        super(expectedCalls);
    }

    public void then(FunctionX<R> behaviour) {
        super.then(behaviour);
    }

    When(VarargsCallable<R> expectedCalls, Class... classes) {
        super(expectedCalls, classes);
    }

    When(When<R> source, VarargsFunction<Boolean> predicate) {
        super(source, predicate);
    }
}
