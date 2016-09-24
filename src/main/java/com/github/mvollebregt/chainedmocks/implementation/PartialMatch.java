package com.github.mvollebregt.chainedmocks.implementation;

import java.util.ArrayList;
import java.util.List;

class PartialMatch {

    private final Object[] wildcards;
    private final List<Object> returnValues = new ArrayList<>();

    PartialMatch(int numberOfWildcards) {
        this.wildcards = new Object[numberOfWildcards];
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
