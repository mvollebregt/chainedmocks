package com.github.mvollebregt.wildmock.exceptions;

import com.github.mvollebregt.wildmock.api.PartialMatch;

public class VerifyClauseNotSatisfiedException extends VerificationException {

    private final PartialMatch closestMatch;

    public VerifyClauseNotSatisfiedException(PartialMatch closestMatch) {
        super(String.join("\n\n", "Verify clause was not satisfied.",
                "The expected call sequence in the verify clause was not observed.",
                closestMatch.toString()));
        this.closestMatch = closestMatch;
    }

    public PartialMatch getClosestMatch() {
        return closestMatch;
    }

}
