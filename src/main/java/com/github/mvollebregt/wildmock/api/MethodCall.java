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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.joining;

public class MethodCall {

    private final Object target;
    private final Method method;
    private final Object[] arguments;
    private final Object returnValue;
    private final Map<Integer, Integer> wildcardMarkers;

    public MethodCall(Object target, Method method, Object[] arguments, Object returnValue) {
        this(target, method, arguments, returnValue, emptyMap());
    }

    public MethodCall(Object target, Method method, Object[] arguments, Object returnValue,
                      Map<Integer, Integer> wildcardMarkers) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.returnValue = returnValue;
        this.wildcardMarkers = wildcardMarkers;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public Map<Integer, Integer> getWildcardMarkers() {
        return wildcardMarkers;
    }

    public MatchLevel match(MethodCall other) {
        if (!target.equals(other.target)) {
            return MatchLevel.NONE;
        } else if (!method.getName().equals(other.method.getName())) {
            return MatchLevel.TARGET;
        } else if (!method.equals(other.method)) {
            return MatchLevel.METHOD_NAME;
        } else if (!argumentsMatch(other)) {
            return MatchLevel.METHOD;
        } else {
            return MatchLevel.COMPLETE;
        }
    }

    private boolean argumentsMatch(MethodCall other) {
        return IntStream.range(0, arguments.length).
                filter(argumentIndex -> !other.wildcardMarkers.keySet().contains(argumentIndex)).
                allMatch(argumentIndex ->
                        arguments[argumentIndex].equals(
                                other.arguments[argumentIndex]));
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodCall that = (MethodCall) o;

        if (!target.equals(that.target)) return false;
        if (!method.equals(that.method)) return false;
        if (!Arrays.equals(arguments, that.arguments)) return false;
        return returnValue != null ? returnValue.equals(that.returnValue) : that.returnValue == null;

    }

    @Override
    public int hashCode() {
        int result = target.hashCode();
        result = 31 * result + method.hashCode();
        result = 31 * result + Arrays.hashCode(arguments);
        result = 31 * result + (returnValue != null ? returnValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return toString(emptyMap());
    }

    String toString(Map<Object, String> argumentReplacements) {
        return String.format("%s.%s(%s)", target, method.getName(),
                IntStream.range(0, arguments.length).boxed().map(index ->
                        argumentToString(index, argumentReplacements)).collect(joining(", ")));
    }

    private String argumentToString(int argumentIndex, Map<Object, String> argumentReplacements) {
        Object argument = arguments[argumentIndex];
        if (wildcardMarkers.containsKey(argumentIndex)) {
            String typeName = argument.getClass().getSimpleName();
            String article = "aeiouAEIOU".contains(typeName.substring(0, 1)) ? "an" : "a";
            return String.format("%s %s", article, typeName);
        } else {
            String outputWildcard = argumentReplacements.get(argument);
            return Optional.ofNullable(outputWildcard).orElse(String.valueOf(argument));
        }
    }
}
