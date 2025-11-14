/*
 * ConstantsTests.java
 *
 * Copyright (c) 2016-2024, Erik C. Thauvin (erik@thauvin.net)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *   Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *   Neither the name of this project nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.thauvin.erik.semver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The <code>ConstantsTests</code> class.
 *
 * @author <a href="https://erik.thauvin.net/" target="_blank">Erik C. Thauvin</a>
 * @created 2019-04-14
 * @since 1.0
 */
class ConstantsTests {
    @ParameterizedTest
    @ValueSource(strings = {
            Constants.DEFAULT_TEMPLATE_NAME,
            Constants.DEFAULT_JAVA_TEMPLATE,
            Constants.DEFAULT_KOTLIN_TEMPLATE})
    void mustacheDefaultTemplates(String input) {
        assertTrue(input.endsWith(".mustache"), input + " should end with .mustache");
    }

    @Nested
    @DisplayName("Defaults Tests")
    class DefaultTests {
        @Test
        void defaultBuildMeta() {
            assertEquals("+", Constants.DEFAULT_BUILDMETA_PREFIX, "buildMeta");
        }

        @Test
        void defaultMajor() {
            assertEquals(1, Constants.DEFAULT_MAJOR, "major");
        }

        @Test
        void defaultMinor() {
            assertEquals(0, Constants.DEFAULT_MINOR, "minor");
        }

        @Test
        void defaultPatch() {
            assertEquals(0, Constants.DEFAULT_PATCH, "patch");
        }

        @Test
        void defaultPreRelease() {
            assertEquals("-", Constants.DEFAULT_PRERELEASE_PREFIX, "preRelease");
        }

        @Test
        void defaultSeparator() {
            assertEquals(".", Constants.DEFAULT_SEPARATOR, "separator");
        }
    }
}
