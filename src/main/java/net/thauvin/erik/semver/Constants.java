/*
 * Constants.java
 *
 * Copyright (c) 2016, Erik C. Thauvin (erik@thauvin.net)
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

/**
 * The <code>Constants</code> class holds the constant variables used throughout this project.
 *
 * @author <a href="mailto:erik@thauvin.net" target="_blank">Erik C. Thauvin</a>
 * @created 2016-01-13
 * @since 1.0
 */
public final class Constants {
    /**
     * The default class name.
     */
    public static final String DEFAULT_CLASS_NAME = "GeneratedVersion";
    /**
     * The default mustache template.
     */
    public static final String DEFAULT_TEMPLATE = "version.mustache";
    /**
     * /**
     * The default Java mustache template.
     */
    public static final String DEFAULT_JAVA_TEMPLATE = "semver.mustache";
    /**
     * The default java type.
     **/
    public static final String DEFAULT_JAVA_TYPE = "java";
    /**
     * The default Kotlin mustache template.
     */
    public static final String DEFAULT_KOTLIN_TEMPLATE = "semver-kt.mustache";
    /**
     * The default major version.
     */
    public static final int DEFAULT_MAJOR = 1;
    /**
     * The default minor version.
     */
    public static final int DEFAULT_MINOR = 0;
    /**
     * The default patch version.
     */
    public static final int DEFAULT_PATCH = 0;
    /**
     * The empty string.
     */
    public static final String EMPTY = "";

    /**
     * The build metadata property key.
     */
    public static final String KEY_VERSION_BUILDMETA = "version.buildmeta";
    /**
     * The major version property key.
     */
    public static final String KEY_VERSION_MAJOR = "version.major";
    /**
     * The minor version property key.
     */
    public static final String KEY_VERSION_MINOR = "version.minor";
    /**
     * The patch version property key.
     */
    public static final String KEY_VERSION_PATCH = "version.patch";
    /**
     * The pre-release version property key.
     */
    public static final String KEY_VERSION_PRERELEASE = "version.prerelease";
    /**
     * The project property key.
     */
    public static final String KEY_VERSION_PROJECT = "version.project";
    /**
     * The kotlin type.
     */
    public static final String KOTLIN_TYPE = "kt";

    /**
     * Disables the default constructor.
     *
     * @throws UnsupportedOperationException if the constructor is called.
     */
    private Constants()
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Illegal constructor call.");
    }
}