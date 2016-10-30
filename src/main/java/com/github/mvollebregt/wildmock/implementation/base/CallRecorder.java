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

import com.github.mvollebregt.wildmock.api.MethodCall;
import com.github.mvollebregt.wildmock.function.VarargsCallable;

import java.util.List;

public class CallRecorder {

    private final CallInterceptor defaultInterceptor;
    private CallInterceptor currentInterceptor;

    public CallRecorder(CallInterceptor defaultInterceptor) {
        this.defaultInterceptor = defaultInterceptor;
        currentInterceptor = defaultInterceptor;
    }

    public CallInterceptor getCurrentInterceptor() {
        return currentInterceptor;
    }

    public List<MethodCall> record(VarargsCallable action, Object[] wildcards, CallInterceptor interceptor) {
        this.currentInterceptor = interceptor;
        action.apply(wildcards);
        this.currentInterceptor = defaultInterceptor;
        return interceptor.getRecordedCalls();
    }
}
