/*
 * VersionProcessorTest.java
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

import com.github.mustachejava.Mustache;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The <code>VersionProcessorTest</code> class.
 *
 * @author <a href="https://erik.thauvin.net/" target="_blank">Erik C. Thauvin</a>
 * @created 2019-04-02
 * @since 1.2.0
 */
class VersionProcessorTest {
    private final VersionProcessor processor = new VersionProcessor();
    private final VersionTest version = new VersionTest();

    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    @Test
    void testCompileTemplate()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        final var method = processor.getClass().getDeclaredMethod("compileTemplate", File.class, String.class);
        method.setAccessible(true);

        final var mustache = (Mustache) method.invoke(processor, new File("src/main/resources"),
                Constants.DEFAULT_JAVA_TEMPLATE);

        assertEquals(Constants.DEFAULT_JAVA_TEMPLATE, mustache.getName(), Constants.DEFAULT_JAVA_TEMPLATE);

        try (var writer = new StringWriter()) {
            mustache.execute(writer, version).flush();
            assertEquals(String.format("""
                                    /*
                                    * This file is automatically generated.
                                    * Do not modify! -- ALL CHANGES WILL BE ERASED!
                                    */
                                    
                                    package %s;
                                    
                                    import java.util.Date;
                                    
                                    /**
                                    * Provides semantic version information.
                                    *
                                    * @author <a href="https://github.com/ethauvin/semver">Semantic Version Annotation Processor</a>
                                    */
                                    public final class MyTest {
                                    public static final String PROJECT = "%s";
                                    public static final Date BUILDDATE = new Date(L);
                                    public static final int MAJOR = %d;
                                    public static final int MINOR = %d;
                                    public static final int PATCH = %d;
                                    public static final String PRERELEASE = "%s";
                                    public static final String PRERELEASE_PREFIX = "%s";
                                    public static final String BUILDMETA = "%s";
                                    public static final String BUILDMETA_PREFIX = "%s";
                                    public static final String SEPARATOR = "%s";
                                    public static final String VERSION = "";
                                    
                                    /**
                                    * Disables the default constructor.
                                    */
                                    private MyTest() {
                                    throw new UnsupportedOperationException("Illegal constructor call.");
                                    }
                                    }
                                    """, version.packageName(), version.project(), version.major(), version.minor(), version.patch(),
                            version.preRelease(), version.preReleasePrefix(), version.buildMeta(),
                            version.buildMetaPrefix(), version.separator()),
                    writer.toString());
        }
    }

    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    @Test
    void testFindValues() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final var method = processor.getClass().getDeclaredMethod("findValues", Version.class);
        method.setAccessible(true);
        final var versionInfo = (VersionInfo) method.invoke(processor, version);

        assertEquals("0-0-7:vodka++martini", versionInfo.getVersion(), "getVersion(0-0-7:vodka++martin)");
        assertEquals("James Bond", versionInfo.getProject(), "getProject(James Bond)");
    }

    @Test
    void testGetSupportedAnnotationTypes() {
        assertTrue(processor.getSupportedAnnotationTypes().contains("net.thauvin.erik.semver.Version"));
    }

    @Test
    void testGetSupportedSourceVersion() {
        assertTrue(processor.getSupportedSourceVersion().ordinal() >= 17);
    }

    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    @Test
    void testGetTemplate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final var method = processor.getClass().getDeclaredMethod("getTemplate", boolean.class, Version.class);
        method.setAccessible(true);

        assertEquals(version.template(), method.invoke(processor, true, version), version.template);
        version.setTemplate(Constants.DEFAULT_JAVA_TEMPLATE);
        assertEquals(Constants.DEFAULT_TEMPLATE_NAME, method.invoke(processor, true, version),
                "default");
        assertEquals(Constants.DEFAULT_KOTLIN_TEMPLATE, method.invoke(processor, false, version),
                "kotlin");
    }

    @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
    @Test
    void testParseIntProperty() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final var p = new Properties();
        p.setProperty("1", "1");
        p.setProperty("2", "2.1");
        p.setProperty("3", "zero");
        p.setProperty("4", " 4 ");

        final var method = processor.getClass().getDeclaredMethod("parseIntProperty", Properties.class, String.class,
                int.class);
        method.setAccessible(true);

        assertEquals(1, method.invoke(processor, p, "1", -1), "parseIntProperty(1)");
        assertEquals(-1, method.invoke(processor, p, "2", -1), "parseIntProperty(2.1)");
        assertEquals(-1, method.invoke(processor, p, "3", -1), "parseIntProperty(zero)");
        assertEquals(4, method.invoke(processor, p, "4", -1), "parseIntProperty( 4 )");
    }
}
