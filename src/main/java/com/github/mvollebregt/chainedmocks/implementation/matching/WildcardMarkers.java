package com.github.mvollebregt.chainedmocks.implementation.matching;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;

class WildcardMarkers {

    private final Map<Integer, List<WildcardMarker>> wildcardMarkers = new HashMap<>();

    void put(int methodCallIndex, int argumentIndex, int wildcardIndex) {
        List<WildcardMarker> markers = wildcardMarkers.get(methodCallIndex);
        if (markers == null) {
            markers = new ArrayList<>();
            wildcardMarkers.put(methodCallIndex, markers);
        }
        markers.add(new WildcardMarker(argumentIndex, wildcardIndex));
    }

    Set<Integer> getArgumentIndicesForWildcards(int methodCallIndex) {
        List<WildcardMarker> markers = wildcardMarkers.get(methodCallIndex);
        if (markers != null) {
            return markers.stream().map(WildcardMarker::getArgumentIndex).collect(Collectors.toSet());
        } else {
            return emptySet();
        }
    }

    WildcardValues matchArguments(int methodCallIndex, Object[] arguments) {
        List<WildcardMarker> markers = wildcardMarkers.get(methodCallIndex);
        if (markers != null) {
            return new WildcardValues(markers.stream().collect(Collectors.toMap(
                    WildcardMarker::getWildcardIndex, marker -> arguments[marker.getArgumentIndex()])));
        } else {
            return new WildcardValues();
        }
    }

    private static class WildcardMarker {

        private final int argumentIndex;
        private final int wildcardIndex;

        WildcardMarker(int argumentIndex, int wildcardIndex) {
            this.argumentIndex = argumentIndex;
            this.wildcardIndex = wildcardIndex;
        }

        int getArgumentIndex() {
            return argumentIndex;
        }

        int getWildcardIndex() {
            return wildcardIndex;
        }
    }
}
