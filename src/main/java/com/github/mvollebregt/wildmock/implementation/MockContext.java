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

package com.github.mvollebregt.wildmock.implementation;

import com.github.mvollebregt.wildmock.exceptions.VerifyClauseNotSatisfiedException;
import com.github.mvollebregt.wildmock.function.VarargsCallable;
import com.github.mvollebregt.wildmock.implementation.base.CallInterceptor;
import com.github.mvollebregt.wildmock.implementation.base.CallRecorder;
import com.github.mvollebregt.wildmock.implementation.matching.CallMatcher;

import java.util.List;

public class MockContext {

    private final static MockContext mockContext = new MockContext();

    private final StubbingCallInterceptor actualCallInterceptor = new StubbingCallInterceptor();
    private final CallRecorder callRecorder = new CallRecorder(actualCallInterceptor);

    public static MockContext getMockContext() {
        return mockContext;
    }

    public void stub(VarargsCallable action, VarargsCallable<Boolean> predicate,
                     VarargsCallable behaviour, Class... wildcardTypes) {
        actualCallInterceptor.addStub(action, predicate, behaviour, wildcardTypes, callRecorder);
    }

    public List<Object[]> verify(VarargsCallable action, Class... wildcardTypes) {
        CallMatcher callMatcher = new CallMatcher(action, null, null, wildcardTypes, callRecorder);
        List<Object[]> arguments = callMatcher.matches(actualCallInterceptor.getRecordedCalls());
        if (arguments.size() == 0) {
            throw new VerifyClauseNotSatisfiedException(callMatcher.closestMatch());
        }
        return arguments;
    }

    CallInterceptor getCurrentInterceptor() {
        return callRecorder.getCurrentInterceptor();
    }
}
