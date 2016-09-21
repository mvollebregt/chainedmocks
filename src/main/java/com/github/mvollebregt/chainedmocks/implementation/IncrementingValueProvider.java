package com.github.mvollebregt.chainedmocks.implementation;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

class IncrementingValueProvider implements ValueProvider {

    private final Objenesis objenesis = new ObjenesisStd();

    private long seed;

    IncrementingValueProvider(long seed) {
        this.seed = seed;
    }

    @Override
    public Object provide(Class type) {
        seed++;
        if (type.equals(Byte.TYPE)) {
            return (byte) seed;
        } else if (type.equals(Short.TYPE)) {
            return (short) seed;
        }  else if (type.equals(Integer.TYPE)) {
            return (int) seed;
        } else if (type.equals(Long.TYPE)) {
            return seed;
        } else if (type.equals(Float.TYPE)) {
            return (float) seed;
        } else if (type.equals(Double.TYPE)) {
            return (double) seed;
        } else if (type.equals(Boolean.TYPE)) {
            return seed % 2 == 0;
        } else if (type.equals(Character.TYPE)) {
            return (char) seed;
        } else if (type.equals(Void.TYPE)) {
            return null;
        } else {
            return objenesis.newInstance(type);
        }
    }
}
