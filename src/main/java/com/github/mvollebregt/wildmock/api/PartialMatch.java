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

package com.github.mvollebregt.wildmock.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartialMatch {

    private final List<MethodCall> observedMethodCalls;
    private final MethodCall mismatchedMethodCall;
    private final List<MethodCall> remainingMethodCalls;

    public PartialMatch(List<MethodCall> observedMethodCalls, MethodCall mismatchedMethodCall, List<MethodCall> remainingMethodCalls) {
        this.observedMethodCalls = observedMethodCalls;
        this.mismatchedMethodCall = mismatchedMethodCall;
        this.remainingMethodCalls = remainingMethodCalls;
    }

    public List<MethodCall> getObservedMethodCalls() {
        return observedMethodCalls;
    }

    public List<MethodCall> getRemainingMethodCalls() {
        return remainingMethodCalls;
    }

    public MethodCall getMismatchedMethodCall() {
        return mismatchedMethodCall;
    }

    @Override
    public String toString() {

        Map<Object, String> outputWildcards = new HashMap<>();
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("The observed calls were:\n");
        messageBuilder.append(methodCallsToString(1, observedMethodCalls, outputWildcards));
        if (mismatchedMethodCall != null) {
            messageBuilder.append(String.format("--- %d. %s\n", observedMethodCalls.size() + 1,
                    mismatchedMethodCall));
        }
        messageBuilder.append("\nExpected calls:\n");
        if (mismatchedMethodCall != null) {
            messageBuilder.append("--- ");
        }
        messageBuilder.append(methodCallsToString(observedMethodCalls.size() + 1, remainingMethodCalls, outputWildcards));
        return messageBuilder.toString();
    }

    private String methodCallsToString(int firstIndex, List<MethodCall> methodCalls, Map<Object, String> outputWildcards) {
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i < methodCalls.size(); i++) {
            messageBuilder.append(String.format("%d. %s\n", firstIndex + i, methodCalls.get(i).toString(outputWildcards)));
            outputWildcards.put(methodCalls.get(i).getReturnValue(), String.format("output of %d", firstIndex + i));
        }
        return messageBuilder.toString();
    }
}
