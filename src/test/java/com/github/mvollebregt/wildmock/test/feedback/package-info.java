/**
 * Tests for giving usable feedback to the programmer, in case the verification fails, or if the mock setup is wrong.
 * If a user verifies a sequence of method calls, wildmock should not only tell the programmer that the verified
 * sequence was not observed, but it should also provide information on the actual methods that were observed.
 */
package com.github.mvollebregt.wildmock.test.feedback;