package com.github.mvollebregt.chainedmocks.implementation;

import java.util.stream.Stream;

abstract class ValueProvider {

    abstract Object provide(Class type);

    Object[] provide(Class... wildcardTypes) {
        return Stream.of(wildcardTypes).map(this::provide).toArray();
    }
}
