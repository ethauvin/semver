/*
 * VersionInfoTests.java
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

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The <code>VersionInfoTests</code> class.
 *
 * @author <a href="mailto:erik@thauvin.net">Erik C. Thauvin</a>
 * @created 2016-02-03
 * @since 1.0
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
class VersionInfoTests {
    private final VersionInfo versionInfo = new VersionInfo();

    @Nested
    @DisplayName("Get Version Tests")
    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    class GetVersionTests {
        @Test
        void versionDefault() {
            assertEquals("1.0.0", versionInfo.getVersion(), "getVersion(1.0.0)");
        }

        @Test
        void versionSameAsGetServer() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);
            versionInfo.setPatch(1);
            versionInfo.setPreRelease("beta");
            versionInfo.setBuildMeta("001");
            versionInfo.setPreReleasePrefix("+");
            versionInfo.setPreReleasePrefix("-");
            versionInfo.setPreRelease("");
            versionInfo.setBuildMetaPrefix(".");
            versionInfo.setBuildMetaPrefix("+");
            versionInfo.setSeparator("-");

            assertEquals(versionInfo.getSemver(), versionInfo.getVersion(),
                    "getVersion(3-2-1+001) = getSemver(3-2-1+001) ");
        }

        @Test
        void versionSetBuildMeta() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);
            versionInfo.setPatch(1);
            versionInfo.setPreRelease("beta");
            versionInfo.setBuildMeta("001");

            assertEquals("3.2.1-beta+001", versionInfo.getVersion(), "getVersion(3.2.1-beta+001)");
        }

        @Test
        void versionSetBuildMetaPrefix() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);
            versionInfo.setPatch(1);
            versionInfo.setPreRelease("beta");
            versionInfo.setBuildMeta("001");
            versionInfo.setPreReleasePrefix("+");
            versionInfo.setPreReleasePrefix("-");
            versionInfo.setPreRelease("");
            versionInfo.setBuildMetaPrefix(".");

            assertEquals("3.2.1.001", versionInfo.getVersion(), "getVersion(3.2.1.001)");
        }

        @Test
        void versionSetBuildMetaPrefixAndSeparator() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);
            versionInfo.setPatch(1);
            versionInfo.setPreRelease("beta");
            versionInfo.setBuildMeta("001");
            versionInfo.setPreReleasePrefix("+");
            versionInfo.setPreReleasePrefix("-");
            versionInfo.setPreRelease("");
            versionInfo.setBuildMetaPrefix(".");
            versionInfo.setBuildMetaPrefix("+");
            versionInfo.setSeparator("-");

            assertEquals("3-2-1+001", versionInfo.getVersion(), "getVersion(3-2-1+001)");
        }

        @Test
        void versionSetMajor() {
            versionInfo.setMajor(3);

            assertEquals("3.0.0", versionInfo.getVersion(), "getVersion(3.0.0)");
        }

        @Test
        void versionSetMinor() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);

            assertEquals("3.2.0", versionInfo.getVersion(), "getVersion(3.2.0)");
        }

        @Test
        void versionSetPatch() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);
            versionInfo.setPatch(1);

            assertEquals("3.2.1", versionInfo.getVersion(), "getVersion(3.2.1)");
        }

        @Test
        void versionSetPreRelease() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);
            versionInfo.setPatch(1);
            versionInfo.setPreRelease("beta");

            assertEquals("3.2.1-beta", versionInfo.getVersion(), "getVersion(3.2.1-beta)");
        }

        @Test
        void versionSetPreReleasePrefix() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);
            versionInfo.setPatch(1);
            versionInfo.setPreRelease("beta");
            versionInfo.setBuildMeta("001");
            versionInfo.setPreReleasePrefix("+");

            assertEquals("3.2.1+beta+001", versionInfo.getVersion(), "getVersion(3.2.1+beta+001)");
        }

        @Test
        void versionSetPreReleasePrefixAndEmptyRelease() {
            versionInfo.setMajor(3);
            versionInfo.setMinor(2);
            versionInfo.setPatch(1);
            versionInfo.setPreRelease("beta");
            versionInfo.setBuildMeta("001");
            versionInfo.setPreReleasePrefix("+");
            versionInfo.setPreReleasePrefix("-");
            versionInfo.setPreRelease("");

            assertEquals("3.2.1+001", versionInfo.getVersion(), "getVersion(3.2.1+001)");
        }
    }

    @Nested
    @DisplayName("Setters and Getters Tests")
    class SettersAndGettersTests {
        @Test
        void checkEpoch() {
            assertTrue((versionInfo.getEpoch() - Instant.now().toEpochMilli()) < 1000,
                    "buildDate - now < 1s");
        }

        @Test
        void setGetBuildMeta() {
            versionInfo.setBuildMeta("001");
            assertEquals("001", versionInfo.getBuildMeta(), "getBuildMeta(001)");
        }

        @Test
        void setGetBuildMetaPrefix() {
            versionInfo.setBuildMetaPrefix("");
            assertEquals("", versionInfo.getBuildMetaPrefix(), "getBuildMetaPrefix( )");
        }

        @Test
        void setGetClassName() {
            versionInfo.setClassName("Example");

            assertEquals("Example", versionInfo.getClassName(), "getClassName(Example");
        }

        @Test
        void setGetMajor() {
            versionInfo.setMajor(1);
            assertEquals(1, versionInfo.getMajor(), "getMajor(1)");
        }

        @Test
        void setGetMinor() {
            versionInfo.setMinor(2);
            assertEquals(2, versionInfo.getMinor(), "getMinor(2)");
        }

        @Test
        void setGetPackageName() {
            versionInfo.setPackageName("com.example");
            assertEquals("com.example", versionInfo.getPackageName(), "getPackageName(com.example)");
        }

        @Test
        void setGetPath() {
            versionInfo.setPatch(3);
            assertEquals(3, versionInfo.getPatch(), "getPatch(3)");
        }

        @Test
        void setGetPreRelease() {
            versionInfo.setPreRelease("alpha");
            assertEquals("alpha", versionInfo.getPreRelease(), "getPreRelease(alpha)");
        }

        @Test
        void setGetPreReleasePrefix() {
            versionInfo.setPreReleasePrefix(".");

            assertEquals(".", versionInfo.getPreReleasePrefix(), "getPreReleasePrefix(.)");
        }

        @Test
        void setGetProject() {
            versionInfo.setProject("My Example");

            assertEquals("My Example", versionInfo.getProject(), "getProject(My Example)");
        }

        @Test
        void setGetSeparator() {
            versionInfo.setSeparator("-");

            assertEquals("-", versionInfo.getSeparator(), "getSeparator(-)");
        }
    }
}
