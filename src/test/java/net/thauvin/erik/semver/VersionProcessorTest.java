/*
 * VersionProcessorTest.java
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

import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import static org.testng.Assert.assertEquals; // NOPMD

/**
 * The <code>VersionProcessorTest</code> class.
 *
 * @author <a href="https://erik.thauvin.net/" target="_blank">Erik C. Thauvin</a>
 * @created.on 2019-04-02
 * @since 1.2.0
 */
public class VersionProcessorTest {
    private final VersionProcessor processor = new VersionProcessor();
    private final Version version = new VersionTest();

    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    @Test
    public void testFindValues() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method method = processor.getClass().getDeclaredMethod("findValues", Version.class);
        method.setAccessible(true);
        final VersionInfo versionInfo = (VersionInfo) method.invoke(processor, version);

        assertEquals(versionInfo.getVersion(), "0-0-7:vodka++martini", "getVersion(0-0-7:vodka++martin)");
        assertEquals(versionInfo.getProject(), "James Bond", "getProject(James Bond)");
    }

    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    @Test
    public void testParseIntProperty() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Properties p = new Properties();
        p.setProperty("1", "1");
        p.setProperty("2", "2.1");
        p.setProperty("3", "zero");
        p.setProperty("4", " 4 ");

        final Method method = processor.getClass().getDeclaredMethod("parseIntProperty", Properties.class, String.class,
                int.class);
        method.setAccessible(true);

        assertEquals(method.invoke(processor, p, "1", -1), 1, "parseIntProperty(1)");
        assertEquals(method.invoke(processor, p, "2", -1), -1, "parseIntProperty(2.1)");
        assertEquals(method.invoke(processor, p, "3", -1), -1, "parseIntProperty(zero)");
        assertEquals(method.invoke(processor, p, "4", -1), 4, "parseIntProperty( 4 )");
    }
}
