/*
 * Copyright 2016 Michel Vollebregt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mvollebregt.wildmock.test.robustness;

import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

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
