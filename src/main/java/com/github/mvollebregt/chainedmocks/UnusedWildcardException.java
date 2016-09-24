package com.github.mvollebregt.chainedmocks;

public class UnusedWildcardException extends RuntimeException {

    public UnusedWildcardException(Class wildcardType, int i) {
        super(String.format("Unused wildcard of type %s at index %d", wildcardType.getSimpleName(), i));
    }
}
