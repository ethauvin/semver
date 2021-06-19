/*
 * VersionInfoTest.java
 *
 * Copyright (c) 2016-2021, Erik C. Thauvin (erik@thauvin.net)
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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * The <code>VersionInfoTest</code> class.
 *
 * @author <a href="mailto:erik@thauvin.net">Erik C. Thauvin</a>
 * @created 2016-02-03
 * @since 1.0
 */
@SuppressFBWarnings("PRMC_POSSIBLY_REDUNDANT_METHOD_CALLS")
public class VersionInfoTest {
    private VersionInfo versionInfo = new VersionInfo();

    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    @Test
    public void testGetVersion() {
        assertEquals(versionInfo.getVersion(), "1.0.0", "getVersion(1.0.0)");

        versionInfo.setMajor(3);

        assertEquals(versionInfo.getVersion(), "3.0.0", "getVersion(3.0.0)");

        versionInfo.setMinor(2);

        assertEquals(versionInfo.getVersion(), "3.2.0", "getVersion(3.2.0)");

        versionInfo.setPatch(1);

        assertEquals(versionInfo.getVersion(), "3.2.1", "getVersion(3.2.1)");

        versionInfo.setPreRelease("beta");

        assertEquals(versionInfo.getVersion(), "3.2.1-beta", "getVersion(3.2.1-beta)");

        versionInfo.setBuildMeta("001");

        assertEquals(versionInfo.getVersion(), "3.2.1-beta+001", "getVersion(3.2.1-beta+001)");

        versionInfo.setPreReleasePrefix("+");

        assertEquals(versionInfo.getVersion(), "3.2.1+beta+001", "getVersion(3.2.1+beta+001)");

        versionInfo.setPreReleasePrefix("-");

        versionInfo.setPreRelease("");

        assertEquals(versionInfo.getVersion(), "3.2.1+001", "getVersion(3.2.1+001)");

        versionInfo.setBuildMetaPrefix(".");

        assertEquals(versionInfo.getVersion(), "3.2.1.001", "getVersion(3.2.1.001)");

        versionInfo.setBuildMetaPrefix("+");

        versionInfo.setSeparator("-");

        assertEquals(versionInfo.getVersion(), "3-2-1+001", "getVersion(3-2-1+001)");

        assertEquals(versionInfo.getVersion(), versionInfo.getSemver(),
                "getVersion(3-2-1+001) = getSemver(3-2-1+001) ");
    }

    @Test
    public void testSetGet() {
        versionInfo.setSeparator(".");

        versionInfo.setMajor(1);

        assertEquals(versionInfo.getMajor(), 1, "getMajor(1)");

        versionInfo.setMinor(2);

        assertEquals(versionInfo.getMinor(), 2, "getMinor(2)");

        versionInfo.setPatch(3);

        assertEquals(versionInfo.getPatch(), 3, "getPatch(3)");

        versionInfo.setPreRelease("alpha");

        assertEquals(versionInfo.getPreRelease(), "alpha", "getPreRelease(alpha)");

        versionInfo.setBuildMeta("001");

        assertEquals(versionInfo.getBuildMeta(), "001", "getBuildMeta(001)");

        versionInfo.setPackageName("com.example");

        assertEquals(versionInfo.getPackageName(), "com.example", "getPackageName(com.example)");

        assertEquals(versionInfo.getVersion(), "1.2.3-alpha+001", "getVersion(1.2.3-alpha+001)");

        assertEquals(versionInfo.getVersion(), versionInfo.getSemver(), "getVersion() = getSemver()");

        versionInfo.setBuildMetaPrefix("");

        assertEquals(versionInfo.getBuildMetaPrefix(), "", "getBuildMetaPrefix( )");

        assertEquals(versionInfo.getVersion(), "1.2.3-alpha001", "getVersion(1.2.3+alpha001)");

        versionInfo.setPreReleasePrefix(".");

        assertEquals(versionInfo.getPreReleasePrefix(), ".", "getPreReleasePrefix(.)");

        assertEquals(versionInfo.getVersion(), "1.2.3.alpha001", "getVersion(1.2.3.alpha001");

        versionInfo.setSeparator("-");

        assertEquals(versionInfo.getSeparator(), "-", "getSeparator(-)");

        assertEquals(versionInfo.getVersion(), "1-2-3.alpha001", "getVersion(1-2-3.alpha001)");

        versionInfo.setProject("My Example");

        assertEquals(versionInfo.getProject(), "My Example", "getProject(My Example)");

        versionInfo.setClassName("Example");

        assertEquals(versionInfo.getClassName(), "Example", "getClassName(Example");

        Assert.assertTrue((versionInfo.getEpoch() - new Date().getTime()) < 1000, "buildDate - now < 1s");
    }

    @Test
    public void testVersionInfo() {
        final Version version = new VersionTest();
        versionInfo = new VersionInfo(version);

        assertEquals(versionInfo.getMajor(), version.major(), "getMajor(major)");

        assertEquals(versionInfo.getMinor(), version.minor(), "getMinor(minor)");

        assertEquals(versionInfo.getPatch(), version.patch(), "getPatch(patch)");

        assertEquals(versionInfo.getPreRelease(), version.preRelease(), "getPreRelease(preRelease)");

        assertEquals(versionInfo.getPreReleasePrefix(), version.preReleasePrefix(),
                "getPreReleasePrefix(preReleasePrefix)");

        assertEquals(versionInfo.getBuildMeta(), version.buildMeta(), "getBuildMeta(buildMeta)");

        assertEquals(versionInfo.getBuildMetaPrefix(), version.buildMetaPrefix(),
                "getBuildMetaPrefix(buildMetaPrefix)");

        assertEquals(versionInfo.getPackageName(), version.packageName(), "getPackageName(packageName)");

        assertEquals(versionInfo.getVersion(),
                version.major() + "." + version.minor() + '.' + version.patch() + '-' + version.preRelease() + '+'
                        + version.buildMeta(), "getVersion(version)");

        assertEquals(versionInfo.getVersion(), versionInfo.getSemver(), "getVersion(version) = getSemver(version)");

        assertEquals(versionInfo.getProject(), version.project(), "getProject(project)");

        assertEquals(versionInfo.getClassName(), version.className(), "getClassName(className)");
    }
}
