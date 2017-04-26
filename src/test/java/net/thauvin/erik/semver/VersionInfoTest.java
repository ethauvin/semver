/*
 * VersionInfoTest.java
 *
 * Copyright (c) 2016-2017, Erik C. Thauvin (erik@thauvin.net)
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
public class VersionInfoTest {
    @Test
    public void testGetVersion()
            throws Exception {

        final Calendar now = Calendar.getInstance();
        final VersionInfo version = new VersionInfo();

        Assert.assertEquals(version.getVersion(), "1.0.0", "getVersion(1.0.0)");

        version.setMajor(3);

        Assert.assertEquals(version.getVersion(), "3.0.0", "getVersion(3.0.0)");

        version.setMinor(2);

        Assert.assertEquals(version.getVersion(), "3.2.0", "getVersion(3.2.0)");

        version.setPatch(1);

        Assert.assertEquals(version.getVersion(), "3.2.1", "getVersion(3.2.1)");

        version.setPreRelease("beta");

        Assert.assertEquals(version.getVersion(), "3.2.1-beta", "getVersion(3.2.1-beta)");

        version.setBuildMeta("001");

        Assert.assertEquals(version.getVersion(), "3.2.1-beta+001", "getVersion(3.2.1-beta+001)");

        version.setPreRelease("");

        Assert.assertEquals(version.getVersion(), "3.2.1+001", "getVersion(3.2.1+001)");

        version.setMajor(1);

        Assert.assertEquals(version.getMajor(), 1, "getMajor(1)");

        version.setMinor(2);

        Assert.assertEquals(version.getMinor(), 2, "getMinor(2)");

        version.setPatch(3);

        Assert.assertEquals(version.getPatch(), 3, "getPatch(3)");

        version.setPreRelease("alpha");

        Assert.assertEquals(version.getPreRelease(), "alpha", "getPreRelease(alpha)");

        version.setBuildMeta("007");

        Assert.assertEquals(version.getBuildMeta(), "007", "getBuildMeta(007)");

        version.setPackageName("com.example");

        Assert.assertEquals(version.getPackageName(), "com.example", "getPackageName(com.example)");

        Assert.assertEquals(version.getVersion(), "1.2.3-alpha+007", "getVersion(1.2.3-alpha+007)");

        version.setProject("My Example");

        Assert.assertEquals(version.getProject(), "My Example", "getProject(My Example)");

        version.setClassName("Example");

        Assert.assertEquals(version.getClassName(), "Example", "getClassName(Example");

        Assert.assertTrue((version.getEpoch() - now.getTimeInMillis()) < 1000,
                "buildDate - now < 1s");
    }
}