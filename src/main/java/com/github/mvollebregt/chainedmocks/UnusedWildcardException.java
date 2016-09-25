package com.github.mvollebregt.chainedmocks;

import java.util.List;
import java.util.stream.Collectors;

public class UnusedWildcardException extends RuntimeException {

    public UnusedWildcardException(List<Integer> unusedWildcardIndices) {
        super(String.format("Unused wildcards at index %s",
                unusedWildcardIndices.stream().map(Object::toString).collect(Collectors.joining(", "))));
    }
}
