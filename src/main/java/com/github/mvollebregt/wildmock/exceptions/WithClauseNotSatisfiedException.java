package com.github.mvollebregt.wildmock.exceptions;

import java.util.Arrays;
import java.util.List;

/**
 * Verification exception that is thrown when the with clause of a verify statement is not satisfied.
 * This means that the expected call sequence in the verify clause was observed, but the observed input wildcards did
 * not satisfy the predicate specified in the with clause.
 */
public class WithClauseNotSatisfiedException extends VerificationException {

    private List<Object[]> observedWildcards;

    public WithClauseNotSatisfiedException(List<Object[]> observedWildcards) {
        super(formatMessage(observedWildcards));
        this.observedWildcards = observedWildcards;
    }

    public List<Object[]> getObservedWildcards() {
        return observedWildcards;
    }

    private static String formatMessage(List<Object[]> observedWildcards) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("With clause was not satisfied.\n\n");
        messageBuilder.append("The expected call sequence in the verify clause was observed, but the observed \n");
        messageBuilder.append("input wildcards did not satisfy the predicate specified in the with clause.\n\n");
        if (observedWildcards.size() > 1) {
            messageBuilder.append(
                    String.format("The call sequence in the verify clause was matched %d times.\n",
                            observedWildcards.size()));
            messageBuilder.append("The observed wildcards were: \n\n");
            for (int i = 0; i < observedWildcards.size(); i++) {
                messageBuilder.append(String.format("%d. %s\n", i + 1, Arrays.toString(observedWildcards.get(i))));
            }
        } else {
            messageBuilder.append("The observed wildcards were: \n\n");
            messageBuilder.append(Arrays.toString(observedWildcards.get(0)));
        }
        return messageBuilder.toString();
    }


}
