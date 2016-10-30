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

import com.github.mvollebregt.wildmock.exceptions.VerificationException;
import com.github.mvollebregt.wildmock.test.helpers.ClassToBeMocked;
import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.mock;
import static com.github.mvollebregt.wildmock.Wildmock.verify;
import static org.junit.jupiter.api.Assertions.expectThrows;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class ConditionalsTest {

    private final ClassToBeMocked mock = mock(ClassToBeMocked.class);

    @Test
    public void testVariableNumberOfCalls() {
        // when
        mock.identityFunction(2);
        // then: this should fail:
        expectThrows(VerificationException.class, () -> verify((wildcard) -> {
            mock.identityFunction(wildcard);
            if (wildcard == 2) {
                mock.action();
            }
        }, Integer.class));
    }

    @Test
    public void testWildcardInConditional() {
        // when
        mock.identityFunction(2);
        mock.consume(3);
        // then: this should succeed
        verify((a, b) -> {
            mock.identityFunction(a);
            if (a == 2) {
                mock.consume(b);
            }
        }, Integer.class, Integer.class);
    }
}
