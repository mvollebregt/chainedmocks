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

/**
 * Tests for giving usable feedback to the programmer, in case the verification fails, or if the mock setup is wrong.
 * If a user verifies a sequence of method calls, wildmock should not only tell the programmer that the verified
 * sequence was not observed, but it should also provide information on the actual methods that were observed.
 */
package com.github.mvollebregt.wildmock.test.feedback;