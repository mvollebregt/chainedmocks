package com.github.mvollebregt.wildmock.exceptions;

public class VerificationException extends RuntimeException {

    public VerificationException() {
    }

    VerificationException(String message) {
        super(message);
    }

}
