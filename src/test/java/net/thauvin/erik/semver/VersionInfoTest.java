/*
 * VersionInfoTest.java
 *
 * Copyright (c) 2016-2019, Erik C. Thauvin (erik@thauvin.net)
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

import java.util.Calendar;

/**
 * The <code>VersionInfoTest</code> class.
 *
 * @author <a href="mailto:erik@thauvin.net">Erik C. Thauvin</a>
 * @created 2016-02-03
 * @since 1.0
 */
@SuppressFBWarnings("PRMC_POSSIBLY_REDUNDANT_METHOD_CALLS")
public class VersionInfoTest {
    private final Calendar now = Calendar.getInstance();
    private VersionInfo versionInfo = new VersionInfo();

    @Test
    public void testGetVersion() {
        Assert.assertEquals(versionInfo.getVersion(), "1.0.0", "getVersion(1.0.0)");

        versionInfo.setMajor(3);

        Assert.assertEquals(versionInfo.getVersion(), "3.0.0", "getVersion(3.0.0)");

        versionInfo.setMinor(2);

        Assert.assertEquals(versionInfo.getVersion(), "3.2.0", "getVersion(3.2.0)");

        versionInfo.setPatch(1);

        Assert.assertEquals(versionInfo.getVersion(), "3.2.1", "getVersion(3.2.1)");

        versionInfo.setPreRelease("beta");

        Assert.assertEquals(versionInfo.getVersion(), "3.2.1-beta", "getVersion(3.2.1-beta)");

        versionInfo.setBuildMeta("001");

        Assert.assertEquals(versionInfo.getVersion(), "3.2.1-beta+001", "getVersion(3.2.1-beta+001)");

        versionInfo.setPreReleasePrefix("+");

        Assert.assertEquals(versionInfo.getVersion(), "3.2.1+beta+001", "getVersion(3.2.1+beta+001)");

        versionInfo.setPreReleasePrefix("-");

        versionInfo.setPreRelease("");

        Assert.assertEquals(versionInfo.getVersion(), "3.2.1+001", "getVersion(3.2.1+001)");

        versionInfo.setBuildMetaPrefix(".");

        Assert.assertEquals(versionInfo.getVersion(), "3.2.1.001", "getVersion(3.2.1.001)");

        versionInfo.setBuildMetaPrefix("+");

        versionInfo.setSeparator("-");

        Assert.assertEquals(versionInfo.getVersion(), "3-2-1+001", "getVersion(3-2-1+001)");

        Assert.assertEquals(versionInfo.getVersion(), versionInfo.getSemver(),
            "getVersion(3-2-1+001) = getSemver(3-2-1+001) ");
    }

    @Test
    public void testSetGet() {
        versionInfo.setSeparator(".");

        versionInfo.setMajor(1);

        Assert.assertEquals(versionInfo.getMajor(), 1, "getMajor(1)");

        versionInfo.setMinor(2);

        Assert.assertEquals(versionInfo.getMinor(), 2, "getMinor(2)");

        versionInfo.setPatch(3);

        Assert.assertEquals(versionInfo.getPatch(), 3, "getPatch(3)");

        versionInfo.setPreRelease("alpha");

        Assert.assertEquals(versionInfo.getPreRelease(), "alpha", "getPreRelease(alpha)");

        versionInfo.setBuildMeta("001");

        Assert.assertEquals(versionInfo.getBuildMeta(), "001", "getBuildMeta(001)");

        versionInfo.setPackageName("com.example");

        Assert.assertEquals(versionInfo.getPackageName(), "com.example", "getPackageName(com.example)");

        Assert.assertEquals(versionInfo.getVersion(), "1.2.3-alpha+001", "getVersion(1.2.3-alpha+001)");

        Assert.assertEquals(versionInfo.getVersion(), versionInfo.getSemver(), "getVersion() = getSemver()");

        versionInfo.setBuildMetaPrefix("");

        Assert.assertEquals(versionInfo.getBuildMetaPrefix(), "", "getBuildMetaPrefix( )");

        Assert.assertEquals(versionInfo.getVersion(), "1.2.3-alpha001", "getVersion(1.2.3+alpha001)");

        versionInfo.setPreReleasePrefix(".");

        Assert.assertEquals(versionInfo.getPreReleasePrefix(), ".", "getPreReleasePrefix(.)");

        Assert.assertEquals(versionInfo.getVersion(), "1.2.3.alpha001", "getVersion(1.2.3.alpha001");

        versionInfo.setSeparator("-");

        Assert.assertEquals(versionInfo.getSeparator(), "-", "getSeparator(-)");

        Assert.assertEquals(versionInfo.getVersion(), "1-2-3.alpha001", "getVersion(1-2-3.alpha001)");

        versionInfo.setProject("My Example");

        Assert.assertEquals(versionInfo.getProject(), "My Example", "getProject(My Example)");

        versionInfo.setClassName("Example");

        Assert.assertEquals(versionInfo.getClassName(), "Example", "getClassName(Example");

        Assert.assertTrue((versionInfo.getEpoch() - now.getTimeInMillis()) < 1000,
            "buildDate - now < 1s");
    }

    @Test
    public void testVersionInfo() {
        final Version version = new VersionTest();
        versionInfo = new VersionInfo(version);

        Assert.assertEquals(versionInfo.getMajor(), version.major(), "getMajor(major)");

        Assert.assertEquals(versionInfo.getMinor(), version.minor(), "getMinor(minor)");

        Assert.assertEquals(versionInfo.getPatch(), version.patch(), "getPatch(patch)");

        Assert.assertEquals(versionInfo.getPreRelease(), version.preRelease(), "getPreRelease(preRelease)");

        Assert.assertEquals(versionInfo.getPreReleasePrefix(), version.preReleasePrefix(),
            "getPreReleasePrefix(preReleasePrefix)");

        Assert.assertEquals(versionInfo.getBuildMeta(), version.buildMeta(), "getBuildMeta(buildMeta)");

        Assert.assertEquals(versionInfo.getBuildMetaPrefix(), version.buildMetaPrefix(),
            "getBuildMetaPrefix(buildMetaPrefix)");

        Assert.assertEquals(versionInfo.getPackageName(), version.packageName(),
            "getPackageName(packageName)");

        Assert.assertEquals(versionInfo.getVersion(),
            version.major()
                + "."
                + version.minor()
                + '.'
                + version.patch()
                + '-'
                + version.preRelease()
                + '+'
                + version.buildMeta(),
            "getVersion(version)");

        Assert.assertEquals(versionInfo.getVersion(), versionInfo.getSemver(),
            "getVersion(version) = getSemver(version)");

        Assert.assertEquals(versionInfo.getProject(), version.project(), "getProject(project)");

        Assert.assertEquals(versionInfo.getClassName(), version.className(), "getClassName(className)");
    }
}
