package com.github.mvollebregt.chainedmocks.implementation;

import java.util.ArrayList;
import java.util.List;

class PartialMatch {

    private final List<Object> returnValues = new ArrayList<>();

    List<Object> getReturnValues() {
        return returnValues;
    }

    int nextIndex() {
        return returnValues.size();
    }

    void add(Object returnValue) {
        returnValues.add(returnValue);
    }
}
