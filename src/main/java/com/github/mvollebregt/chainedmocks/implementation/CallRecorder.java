package com.github.mvollebregt.chainedmocks.implementation;

import com.github.mvollebregt.chainedmocks.function.ParameterisedAction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CallRecorder {

    private boolean recording = false;
    private List<MethodCall> recordedCalls;
    private ValueProvider valueProvider;
    private Map<Object, Integer> wildcardIndices;

    boolean isRecording() {
        return recording;
    }

    List<MethodCall> record(ParameterisedAction action, Object[] wildcards, List<Object> returnValues) {
        return record(action, wildcards, new PrerecordedValueProvider(returnValues));
    }

    List<MethodCall> record(ParameterisedAction action, Object[] wildcards, ValueProvider valueProvider) {
        this.valueProvider = valueProvider;
        wildcardIndices = IntStream.range(0, wildcards.length).boxed().collect(Collectors.toMap(i -> wildcards[i], Function.identity()));
        recordedCalls = new ArrayList<>();
        recording = true;
        action.accept(wildcards);
        recording = false;
        return recordedCalls;
    }

    List<Integer> getUnusedWildcardIndices() {
        return wildcardIndices.values().stream().sorted().collect(Collectors.toList());
    }

    Object registerCall(Object target, Method method, Object[] arguments) {
        // TODO: only find wild cards if we are prerecording!
        List<WildcardMarker> wildcardMarkers = IntStream.range(0, arguments.length).boxed().map(argumentIndex -> {
            Object argument = arguments[argumentIndex];
            Integer wildcardIndex = wildcardIndices.get(argument);
            if (wildcardIndex != null) {
                wildcardIndices.remove(argument);
                return new WildcardMarker(argumentIndex, wildcardIndex);
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());

        Object returnValue = valueProvider.provide(method.getReturnType());
        recordedCalls.add(new MethodCall(target, method, arguments, returnValue, wildcardMarkers));
        return returnValue;
    }
}
