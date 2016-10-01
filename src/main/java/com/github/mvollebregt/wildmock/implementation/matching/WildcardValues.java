package com.github.mvollebregt.wildmock.implementation.matching;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class WildcardValues {

    private final Map<Integer, Object> values;

    WildcardValues() {
        this(new HashMap<>());
    }

    WildcardValues(Object... values) {
        this(IntStream.range(0, values.length).boxed().filter(index -> values[index] != null).
                collect(Collectors.toMap(Function.identity(), index -> values[index])));
    }

    WildcardValues(Map<Integer, Object> values) {
        this.values = values;
    }

    WildcardValues plus(WildcardValues other) {
        if (other.values.isEmpty()) {
            return this;
        } else {
            Map<Integer, Object> newMap = new HashMap<>(this.values);
            other.values.forEach(newMap::put);
            return new WildcardValues(newMap);
        }
    }

    boolean isEmpty() {
        return values.isEmpty();
    }

    Object[] toObjectArray() {
        return IntStream.range(0, values.size()).boxed().map(values::get).collect(Collectors.toList()).toArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WildcardValues that = (WildcardValues) o;

        return values.equals(that.values);

    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }
}
