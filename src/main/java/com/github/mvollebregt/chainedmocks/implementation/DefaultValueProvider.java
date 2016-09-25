package com.github.mvollebregt.chainedmocks.implementation;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.stream.Stream;

class DefaultValueProvider extends ValueProvider {

    Object provide(Class type) {
        return DefaultValueProvider.provideDefault(type);
    }

    static Object provideDefault(Class type) {
        if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
            return (byte) 0;
        } else if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            return (short) 0;
        } else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return 0;
        } else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return 0L;
        } else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            return 0F;
        } else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
            return 0.0;
        } else if (type.equals(Boolean.TYPE) || type.equals(Bool.class)) {
            return false;
        } else if (type.equals(Character.TYPE) || type.equals(Character.class)) {
            return (char) 0;
        } else {
            return null;
        }
    }

    static Object[] provideDefault(Class[] wildcardTypes) {
        return Stream.of(wildcardTypes).map(DefaultValueProvider::provideDefault).toArray();
    }
}
