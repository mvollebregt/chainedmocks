package com.github.mvollebregt.wildmock.test.robustness;

import com.github.mvollebregt.wildmock.fluentinterface.TriggerA;
import com.github.mvollebregt.wildmock.fluentinterface.WhenA;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ReuseOfWhenAndTriggers {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testReuseTrigger() {
        TriggerA<Integer> trigger = trigger(mock::consume, Integer.class);
        assertNotEquals(trigger.with(x1 -> x1 >= 0), trigger.with(x2 -> x2 <= 0));
    }

    @Test
    public void testReuseWhen() {
        WhenA<Integer, Integer> trigger = when(mock::identityFunction, Integer.class);
        assertNotEquals(trigger.with(x -> x >= 0), trigger.with(x -> x <= 0));
    }
}
