package com.github.mvollebregt.wildmock.implementation.matching;

class MatchedValue {

    private final Object returnValue;
    private final WildcardValues wildcards;

    MatchedValue(Object returnValue, WildcardValues wildcards) {
        this.returnValue = returnValue;
        this.wildcards = wildcards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchedValue that = (MatchedValue) o;

        return returnValue != null ? returnValue.equals(that.returnValue) : that.returnValue == null && wildcards.equals(that.wildcards);

    }

    @Override
    public int hashCode() {
        int result = returnValue != null ? returnValue.hashCode() : 0;
        result = 31 * result + wildcards.hashCode();
        return result;
    }
}
