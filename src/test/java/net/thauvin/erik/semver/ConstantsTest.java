/*
 * ConstantsTest.java
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


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The <code>ConstantsTest</code> class.
 *
 * @author <a href="https://erik.thauvin.net/" target="_blank">Erik C. Thauvin</a>
 * @created 2019-04-14
 * @since 1.0
 */
class ConstantsTest {
    @Test
    void testDefaults() {
        assertEquals(1, Constants.DEFAULT_MAJOR, "major");
        assertEquals(0, Constants.DEFAULT_MINOR, "minor");
        assertEquals(0, Constants.DEFAULT_PATCH, "patch");
        assertEquals("-", Constants.DEFAULT_PRERELEASE_PREFIX, "preRelease");
        assertEquals("+", Constants.DEFAULT_BUILDMETA_PREFIX, "buildMeta");
        assertEquals(".", Constants.DEFAULT_SEPARATOR, "separator");
    }

    @Test
    void testTemplates() {
        final List<String> templates = new ArrayList<>();
        templates.add(Constants.DEFAULT_JAVA_TEMPLATE);
        templates.add(Constants.DEFAULT_KOTLIN_TEMPLATE);
        templates.add(Constants.DEFAULT_TEMPLATE_NAME);

        for (final var tp : templates) {
            assertTrue(tp.endsWith(".mustache"), tp);
        }
    }
}
