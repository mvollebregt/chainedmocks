package com.github.mvollebregt.chainedmocks.implementation;

import java.util.List;

class PrerecordedValueProvider implements ValueProvider {

    private final List<Object> prerecordedValues;
    private int i = -1;

    PrerecordedValueProvider(List<Object> prerecordedValues) {
        this.prerecordedValues = prerecordedValues;
    }

    @Override
    public Object provide(Class type) {
        i++;
        return i < prerecordedValues.size() ? prerecordedValues.get(i) : provideDefault(type);
    }

    private Object provideDefault(Class type) {
        if (type.equals(Byte.TYPE)) {
            return (byte) 0;
        } else if (type.equals(Short.TYPE)) {
            return (short) 0;
        }  else if (type.equals(Integer.TYPE)) {
            return 0;
        } else if (type.equals(Long.TYPE)) {
            return 0L;
        } else if (type.equals(Float.TYPE)) {
            return 0F;
        } else if (type.equals(Double.TYPE)) {
            return 0.0;
        } else if (type.equals(Boolean.TYPE)) {
            return false;
        } else if (type.equals(Character.TYPE)) {
            return (char) 0;
        } else {
            return null;
        }
    }
}
