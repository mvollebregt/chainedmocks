package com.github.mvollebregt.wildmock.exceptions;

import com.github.mvollebregt.wildmock.api.PartialMatch;

public class VerifyClauseNotSatisfiedException extends VerificationException {

    private final PartialMatch closestMatch;

    public VerifyClauseNotSatisfiedException(PartialMatch closestMatch) {
        super(formatMessage(closestMatch));
        this.closestMatch = closestMatch;
    }

    public PartialMatch getClosestMatch() {
        return closestMatch;
    }

    private static String formatMessage(PartialMatch closestMatch) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Verify clause was not satisfied.\n\n");
        messageBuilder.append("The expected call sequence in the verify clause was not observed.\n\n");
        messageBuilder.append("The observed calls were:\n");
        for (int i = 0; i < closestMatch.getObservedMethodCalls().size(); i++) {
            messageBuilder.append(String.format("%d. %s\n", 1 + i, closestMatch.getObservedMethodCalls().get(i)));
        }
        if (closestMatch.getMismatchedMethodCall() != null) {
            messageBuilder.append(String.format("--- %d. %s\n", closestMatch.getObservedMethodCalls().size() + 1,
                    closestMatch.getMismatchedMethodCall()));
        }
        messageBuilder.append("\nExpected calls:\n");
        if (closestMatch.getMismatchedMethodCall() != null) {
            messageBuilder.append("--- ");
        }
        for (int i = 0; i < closestMatch.getRemainingMethodCalls().size(); i++) {
            messageBuilder.append(String.format("%d. %s\n", closestMatch.getObservedMethodCalls().size() + 1 + i,
                    closestMatch.getRemainingMethodCalls().get(i)));
        }
        return messageBuilder.toString();
    }

}
