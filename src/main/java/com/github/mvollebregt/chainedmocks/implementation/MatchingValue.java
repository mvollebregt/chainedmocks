package com.github.mvollebregt.chainedmocks.implementation;

import java.util.Map;

class MatchingValue {

    private final Object returnValue;
    private final Map<Integer, Object> wildcards;

    MatchingValue(Object returnValue, Map<Integer, Object> wildcards) {
        this.returnValue = returnValue;
        this.wildcards = wildcards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchingValue that = (MatchingValue) o;

        return returnValue != null ? returnValue.equals(that.returnValue) : that.returnValue == null && wildcards.equals(that.wildcards);

    }

    @Override
    public int hashCode() {
        int result = returnValue != null ? returnValue.hashCode() : 0;
        result = 31 * result + wildcards.hashCode();
        return result;
    }
}