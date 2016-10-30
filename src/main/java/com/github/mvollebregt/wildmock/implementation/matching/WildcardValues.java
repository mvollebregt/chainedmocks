/*
 * Copyright 2016 Michel Vollebregt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mvollebregt.wildmock.implementation.matching;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class WildcardValues {

    private final Map<Integer, Object> values;

    WildcardValues() {
        this(new HashMap<>());
    }

    WildcardValues(Map<Integer, Object> values) {
        this.values = values;
    }

    Optional<Object> get(int i) {
        return Optional.ofNullable(values.get(i));
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
