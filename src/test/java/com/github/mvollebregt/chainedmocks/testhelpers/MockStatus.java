package com.github.mvollebregt.chainedmocks.testhelpers;

public class MockStatus {

    private String message;

    public MockStatus(String statusMessage) {
        this.message = statusMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String status) {
        this.message = status;
    }
}
