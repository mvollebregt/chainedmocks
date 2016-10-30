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

import com.github.mvollebregt.wildmock.function.VarargsCallable;
import com.github.mvollebregt.wildmock.function.VarargsFunction;

import static com.github.mvollebregt.wildmock.implementation.MockContext.getMockContext;

class Stub {

    private final Class[] classes;
    private final VarargsCallable expectedCalls;
    private VarargsFunction<Boolean> predicate;

    Stub(VarargsCallable expectedCalls, Class... classes) {
        this.expectedCalls = expectedCalls;
        this.classes = classes;
    }

    Stub(Stub source, VarargsFunction<Boolean> predicate) {
        this.expectedCalls = source.expectedCalls;
        this.classes = source.classes;
        this.predicate = source.predicate == null ? predicate : arguments ->
                source.predicate.apply(arguments) && predicate.apply(arguments);
    }

    void then(VarargsCallable behaviour) {
        getMockContext().stub(expectedCalls, predicate, behaviour, classes);
    }
}
