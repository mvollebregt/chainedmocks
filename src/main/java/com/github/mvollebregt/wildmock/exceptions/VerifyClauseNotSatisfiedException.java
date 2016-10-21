package com.github.mvollebregt.wildmock.exceptions;

import com.github.mvollebregt.wildmock.api.MethodCall;

import java.util.List;

public class VerifyClauseNotSatisfiedException extends VerificationException {

    private final List<MethodCall> observedMethodCalls;
    private final List<MethodCall> remainingMethodCalls;

    public VerifyClauseNotSatisfiedException(List<MethodCall> observedMethodCalls, List<MethodCall> remainingMethodCalls) {
        super(formatMessage(observedMethodCalls, remainingMethodCalls));
        this.observedMethodCalls = observedMethodCalls;
        this.remainingMethodCalls = remainingMethodCalls;
    }

    public List<MethodCall> getObservedMethodCalls() {
        return observedMethodCalls;
    }

    public List<MethodCall> getRemainingMethodCalls() {
        return remainingMethodCalls;
    }

    private static String formatMessage(List<MethodCall> observedMethodcalls, List<MethodCall> expectedMethodCalls) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Verify clause was not satisfied.\n\n");
        messageBuilder.append("The expected call sequence in the verify clause was not observed.\n\n");
        messageBuilder.append("The observed calls were:\n");
        for (int i = 0; i < observedMethodcalls.size(); i++) {
            messageBuilder.append(String.format("%d. %s\n", 1 + i, observedMethodcalls.get(i)));
        }
        messageBuilder.append("\nExpected calls:\n");
        for (int i = 0; i < expectedMethodCalls.size(); i++) {
            messageBuilder.append(String.format("%d. %s\n", observedMethodcalls.size() + 1 + i, expectedMethodCalls.get(i)));
        }
        return messageBuilder.toString();
    }

}
