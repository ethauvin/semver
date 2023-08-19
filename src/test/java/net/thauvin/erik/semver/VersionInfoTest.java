/*
 * VersionInfoTest.java
 *
 * Copyright (c) 2016-2023, Erik C. Thauvin (erik@thauvin.net)
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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The <code>VersionInfoTest</code> class.
 *
 * @author <a href="mailto:erik@thauvin.net">Erik C. Thauvin</a>
 * @created.on 2016-02-03
 * @since 1.0
 */
public class VersionInfoTest {
    private VersionInfo versionInfo = new VersionInfo();

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    @Test
    public void testGetVersion() {
        assertEquals("1.0.0", versionInfo.getVersion(), "getVersion(1.0.0)");

        versionInfo.setMajor(3);

        assertEquals("3.0.0", versionInfo.getVersion(), "getVersion(3.0.0)");

        versionInfo.setMinor(2);

        assertEquals("3.2.0", versionInfo.getVersion(), "getVersion(3.2.0)");

        versionInfo.setPatch(1);

        assertEquals("3.2.1", versionInfo.getVersion(), "getVersion(3.2.1)");

        versionInfo.setPreRelease("beta");

        assertEquals("3.2.1-beta", versionInfo.getVersion(), "getVersion(3.2.1-beta)");

        versionInfo.setBuildMeta("001");

        assertEquals("3.2.1-beta+001", versionInfo.getVersion(), "getVersion(3.2.1-beta+001)");

        versionInfo.setPreReleasePrefix("+");

        assertEquals("3.2.1+beta+001", versionInfo.getVersion(), "getVersion(3.2.1+beta+001)");

        versionInfo.setPreReleasePrefix("-");

        versionInfo.setPreRelease("");

        assertEquals("3.2.1+001", versionInfo.getVersion(), "getVersion(3.2.1+001)");

        versionInfo.setBuildMetaPrefix(".");

        assertEquals("3.2.1.001", versionInfo.getVersion(), "getVersion(3.2.1.001)");

        versionInfo.setBuildMetaPrefix("+");

        versionInfo.setSeparator("-");

        assertEquals("3-2-1+001", versionInfo.getVersion(), "getVersion(3-2-1+001)");

        assertEquals(versionInfo.getSemver(), versionInfo.getVersion(),
                "getVersion(3-2-1+001) = getSemver(3-2-1+001) ");
    }

    @Test
    public void testSetGet() {
        versionInfo.setSeparator(".");

        versionInfo.setMajor(1);

        assertEquals(1, versionInfo.getMajor(), "getMajor(1)");

        versionInfo.setMinor(2);

        assertEquals(2, versionInfo.getMinor(), "getMinor(2)");

        versionInfo.setPatch(3);

        assertEquals(3, versionInfo.getPatch(), "getPatch(3)");

        versionInfo.setPreRelease("alpha");

        assertEquals("alpha", versionInfo.getPreRelease(), "getPreRelease(alpha)");

        versionInfo.setBuildMeta("001");

        assertEquals("001", versionInfo.getBuildMeta(), "getBuildMeta(001)");

        versionInfo.setPackageName("com.example");

        assertEquals("com.example", versionInfo.getPackageName(), "getPackageName(com.example)");

        assertEquals("1.2.3-alpha+001", versionInfo.getVersion(), "getVersion(1.2.3-alpha+001)");

        assertEquals(versionInfo.getSemver(), versionInfo.getVersion(), "getVersion() = getSemver()");

        versionInfo.setBuildMetaPrefix("");

        assertEquals("", versionInfo.getBuildMetaPrefix(), "getBuildMetaPrefix( )");

        assertEquals("1.2.3-alpha001", versionInfo.getVersion(), "getVersion(1.2.3+alpha001)");

        versionInfo.setPreReleasePrefix(".");

        assertEquals(".", versionInfo.getPreReleasePrefix(), "getPreReleasePrefix(.)");

        assertEquals("1.2.3.alpha001", versionInfo.getVersion(), "getVersion(1.2.3.alpha001");

        versionInfo.setSeparator("-");

        assertEquals("-", versionInfo.getSeparator(), "getSeparator(-)");

        assertEquals("1-2-3.alpha001", versionInfo.getVersion(), "getVersion(1-2-3.alpha001)");

        versionInfo.setProject("My Example");

        assertEquals("My Example", versionInfo.getProject(), "getProject(My Example)");

        versionInfo.setClassName("Example");

        assertEquals("Example", versionInfo.getClassName(), "getClassName(Example");

        assertTrue((versionInfo.getEpoch() - new Date().getTime()) < 1000, "buildDate - now < 1s");
    }

    @Test
    public void testVersionInfo() {
        final Version version = new VersionTest();
        versionInfo = new VersionInfo(version);

        assertEquals(version.major(), versionInfo.getMajor(), "getMajor(major)");

        assertEquals(version.minor(), versionInfo.getMinor(), "getMinor(minor)");

        assertEquals(version.patch(), versionInfo.getPatch(), "getPatch(patch)");

        assertEquals(version.preRelease(), versionInfo.getPreRelease(), "getPreRelease(preRelease)");

        assertEquals(version.preReleasePrefix(), versionInfo.getPreReleasePrefix(),
                "getPreReleasePrefix(preReleasePrefix)");

        assertEquals(version.buildMeta(), versionInfo.getBuildMeta(), "getBuildMeta(buildMeta)");

        assertEquals(version.buildMetaPrefix(), versionInfo.getBuildMetaPrefix(),
                "getBuildMetaPrefix(buildMetaPrefix)");

        assertEquals(version.packageName(), versionInfo.getPackageName(), "getPackageName(packageName)");

        assertEquals(versionInfo.getVersion(),
                version.major() + "." + version.minor() + '.' + version.patch() + '-' + version.preRelease() + '+'
                        + version.buildMeta(), "getVersion(version)");

        assertEquals(versionInfo.getSemver(), versionInfo.getVersion(), "getVersion(version) = getSemver(version)");

        assertEquals(version.project(), versionInfo.getProject(), "getProject(project)");

        assertEquals(version.className(), versionInfo.getClassName(), "getClassName(className)");
    }
}
