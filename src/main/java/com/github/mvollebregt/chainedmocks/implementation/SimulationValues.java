package com.github.mvollebregt.chainedmocks.implementation;

import java.util.ArrayList;
import java.util.List;

class SimulationValues {

    private final Object[] wildcards;
    private final List<Object> returnValues;

    SimulationValues(Object[] wildcards, List<Object> returnValues) {
        this.wildcards = wildcards;
        this.returnValues = returnValues;
    }

    SimulationValues extend(MatchingValue matchingValue) {
        Object[] newWildcards = matchingValue.getWildcards().isEmpty() ? wildcards : wildcards.clone();
        matchingValue.getWildcards().forEach((wildcardIndex, wildcard) -> newWildcards[wildcardIndex] = wildcard);
        List<Object> newReturnValues = new ArrayList<>(returnValues);
        newReturnValues.add(matchingValue.getReturnValue());
        return new SimulationValues(newWildcards, newReturnValues);
    }

    Object[] getWildcards() {
        return wildcards;
    }

    List<Object> getReturnValues() {
        return returnValues;
    }
}
