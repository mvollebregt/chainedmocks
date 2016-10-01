package com.github.mvollebregt.wildmock.exceptions;

public class AmbiguousExpectationsException extends RuntimeException {

    public AmbiguousExpectationsException() {
        super("The called methods in the test match multiple when clauses.");
    }

}
