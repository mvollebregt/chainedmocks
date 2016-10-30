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

package com.github.mvollebregt.wildmock.implementation.matching;

import com.github.mvollebregt.wildmock.api.MethodCall;
import com.github.mvollebregt.wildmock.implementation.base.CallInterceptor;
import com.github.mvollebregt.wildmock.implementation.base.IncrementingValueProvider;

import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

class SimulatingCallInterceptor implements CallInterceptor {

    private int returnValueCount = -1;
    private final List<Object> returnValues;
    private final IncrementingValueProvider valueProvider = new IncrementingValueProvider();
    private final Object[] wildcards;
    private final Map<Object, Integer> wildcardIndices;
    private final List<MethodCall> recordedCalls = new ArrayList<>();

    SimulatingCallInterceptor(Class[] wildcardTypes, WildcardValues wildcardValues, List<Object> returnValues) {
        this.returnValues = returnValues;
        wildcards = IntStream.range(0, wildcardTypes.length).boxed().map(i -> wildcardValues.get(i).orElse(
                valueProvider.provide(wildcardTypes[i]))).toArray();
        wildcardIndices = IntStream.range(0, wildcardTypes.length).boxed().collect(
                toMap(i -> wildcards[i], identity()));
    }

    Object[] getWildcards() {
        return wildcards;
    }

    @Override
    public List<MethodCall> getRecordedCalls() {
        return recordedCalls;
    }

    public Object intercept(Object target, Method method, Object[] arguments) {
        Object returnValue = getReturnValue(method.getReturnType());
        Map<Integer, Integer> wildcardMarkers = matchMethodArgumentsWithWildcards(arguments);
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue, wildcardMarkers));
        return returnValue;
    }

    private Object getReturnValue(Class<?> returnType) {
        returnValueCount++;
        return returnValueCount < returnValues.size() ?
                returnValues.get(returnValueCount) : valueProvider.provide(returnType);
    }

    private Map<Integer, Integer> matchMethodArgumentsWithWildcards(Object[] arguments) {
        return IntStream.range(0, arguments.length).boxed().map(argumentIndex -> {
            Integer wildcardIndex = wildcardIndices.remove(arguments[argumentIndex]);
            return wildcardIndex == null ? null : new SimpleEntry<>(argumentIndex, wildcardIndex);
        }).filter(Objects::nonNull).collect(toMap(Entry::getKey, Entry::getValue));
    }
}
