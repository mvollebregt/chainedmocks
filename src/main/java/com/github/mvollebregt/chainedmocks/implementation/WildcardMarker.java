package com.github.mvollebregt.chainedmocks.implementation;

class WildcardMarker {

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
