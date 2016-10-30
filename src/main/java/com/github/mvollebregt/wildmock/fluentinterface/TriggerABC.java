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

public class TriggerABC<A, B, C> extends Trigger {

    public TriggerABC(ActionABC<A, B, C> expectedCalls, Class<A> a, Class<B> b, Class<C> c) {
        super(expectedCalls, a, b, c);
    }

    public TriggerABC<A, B, C> with(FunctionABC<A, B, C, Boolean> predicate) {
        return new TriggerABC<>(this, predicate);
    }

    public void then(ActionABC<A, B, C> behaviour) {
        super.then(behaviour);
    }

    private TriggerABC(TriggerABC<A, B, C> source, FunctionABC<A, B, C, Boolean> predicate) {
        super(source, predicate);
    }
}
