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

package com.github.mvollebregt.wildmock.examples;

import org.junit.jupiter.api.Test;

import static com.github.mvollebregt.wildmock.Wildmock.*;

/**
 * Examples that are used in the first page of the documentation, demonstrating the main features of Wildmock.
 */
@SuppressWarnings("WeakerAccess")
public class Features {

    public interface SomeClass {
        String getValue();

        int absolute(int input);
    }

    public interface SomeOtherClass {
        void call(String value);
    }

    private SomeClass someMock = mock(SomeClass.class);
    private SomeOtherClass someOtherMock = mock(SomeOtherClass.class);

    @Test
    public void inputWildcards() {
        when(a -> someMock.absolute(a), Integer.class).
                with(a -> a < 0).
                then(a -> -a);
    }


    @Test
    public void outputWildcards() {
        someMock.getValue();
        someOtherMock.call("wildmock!");

        // verify that the output of the first method is passed into the second method
        verify(() -> {
            String value = someMock.getValue();
            someOtherMock.call(value);
        });

    }
}
