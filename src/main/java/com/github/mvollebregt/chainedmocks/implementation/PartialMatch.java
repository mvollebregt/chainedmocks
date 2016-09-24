package com.github.mvollebregt.chainedmocks.implementation;

import java.util.ArrayList;
import java.util.List;

class PartialMatch {

    private final Object[] wildcards;
    private final List<Object> returnValues = new ArrayList<>();

    PartialMatch(Class[] wildcardTypes) {
        wildcards = new Object[wildcardTypes.length];
        for (int i = 0; i < wildcardTypes.length; i++) {
            wildcards[i] = DefaultValueProvider.provideDefault(wildcardTypes[i]);
        }
    }

    Object[] getWildcards() {
        return wildcards;
    }

    List<Object> getReturnValues() {
        return returnValues;
    }

    int nextIndex() {
        return returnValues.size();
    }

    void setWildcard(int index, Object value) {
        wildcards[index] = value;
    }

    void addReturnValue(Object returnValue) {
        returnValues.add(returnValue);
    }

}
