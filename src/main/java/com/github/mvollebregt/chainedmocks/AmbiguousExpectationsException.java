package com.github.mvollebregt.chainedmocks;

public class AmbiguousExpectationsException extends RuntimeException {

    public AmbiguousExpectationsException() {
        super("The called methods in the test match multiple when clauses.");
    }

}
