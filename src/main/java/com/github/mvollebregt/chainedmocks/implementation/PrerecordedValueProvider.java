package com.github.mvollebregt.chainedmocks.implementation;

import java.util.List;

class PrerecordedValueProvider extends ValueProvider {

    private final List<Object> prerecordedValues;
    private int i = -1;

    PrerecordedValueProvider(List<Object> prerecordedValues) {
        this.prerecordedValues = prerecordedValues;
    }

    Object provide(Class type) {
        i++;
        return i < prerecordedValues.size() ? prerecordedValues.get(i) : DefaultValueProvider.provideDefault(type);
    }
}
