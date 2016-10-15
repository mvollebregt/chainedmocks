package com.github.mvollebregt.wildmock.test.robustness;

import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;

public class CombiningStubbingAndVerifyingTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testCombineStubVerify() {
        // given
        trigger(mock::provideInt).then(() -> {
        });
        // when
        int actualValue = mock.provideInt();
        mock.consume(actualValue);
        // then
        verify(() -> {
            int simulatedValue = mock.provideInt();
            mock.consume(simulatedValue);
        });
    }
}
