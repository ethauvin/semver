/*
 * Version.java
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The <code>Version</code> class implements the annotation interface.
 *
 * @author <a href="mailto:erik@thauvin.net" target="_blank">Erik C. Thauvin</a>
 * @created 2016-01-13
 * @since 1.0
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Version {
    /**
     * Returns the build metadata version.
     *
     * @return The build metadata version.
     */
    String buildMeta() default Constants.EMPTY;

    /**
     * Returns the build metadata key.
     *
     * @return The build metadata key.
     */
    String buildMetaKey() default Constants.KEY_VERSION_BUILDMETA;

    /**
     * Returns the build metadata prefix.
     *
     * @return The build metadata prefix.
     */
    String buildMetaPrefix() default Constants.DEFAULT_BUILDMETA_PREFIX;

    /**
     * Returns the build metadata prefix key.
     *
     * @return The build metadata prefix key.
     */
    String buildMetaPrefixKey() default Constants.KEY_VERSION_BUILDMETA_PREFIX;

    /**
     * Returns the class name.
     *
     * @return The class name.
     */
    String className() default Constants.DEFAULT_CLASS_NAME;

    /**
     * Returns the keys prefix.
     *
     * @return The keys prefix.
     */
    String keysPrefix() default Constants.DEFAULT_KEYS_PREFIX;

    /**
     * Returns the major version.
     *
     * @return The major version.
     */
    int major() default Constants.DEFAULT_MAJOR;

    /**
     * Returns the major key.
     *
     * @return The major key.
     */
    String majorKey() default Constants.KEY_VERSION_MAJOR;

    /**
     * Returns the minor version.
     *
     * @return The build minor version.
     */
    int minor() default Constants.DEFAULT_MINOR;

    /**
     * Returns the minor key.
     *
     * @return The minor key.
     */
    String minorKey() default Constants.KEY_VERSION_MINOR;

    /**
     * Returns the package name.
     *
     * @return The package name .
     */
    String packageName() default Constants.EMPTY;

    /**
     * Returns the patch version.
     *
     * @return The patch version.
     */
    int patch() default Constants.DEFAULT_PATCH;

    /**
     * Returns the patch key.
     *
     * @return The patch key.
     */
    String patchKey() default Constants.KEY_VERSION_PATCH;

    /**
     * Returns the pre-release version.
     *
     * @return The pre-release version.
     */
    String preRelease() default Constants.EMPTY;

    /**
     * Returns the pre-release key.
     *
     * @return The pre-release key.
     */
    String preReleaseKey() default Constants.KEY_VERSION_PRERELEASE;

    /**
     * Returns the pre-release prefix.
     *
     * @return The pre-release prefix.
     */
    String preReleasePrefix() default Constants.DEFAULT_PRERELEASE_PREFIX;

    /**
     * Returns the pre-release prefix key.
     *
     * @return The pre-release prefix key.
     */
    String preReleasePrefixKey() default Constants.KEY_VERSION_PRERELEASE_PREFIX;

    /**
     * Returns the project name.
     *
     * @return The project name.
     */
    String project() default Constants.EMPTY;

    /**
     * Returns the project key.
     *
     * @return The project.
     */
    String projectKey() default Constants.KEY_VERSION_PROJECT;

    /**
     * Returns the properties file name.
     *
     * @return The properties file name.
     */
    String properties() default Constants.EMPTY;

    /**
     * Returns the version separator.
     *
     * @return The separator.
     */
    String separator() default Constants.DEFAULT_SEPARATOR;

    /**
     * Returns the version separator key.
     *
     * @return The separator key.
     */
    String separatorKey() default Constants.KEY_VERSION_SEPARATOR;

    /**
     * Returns the template name.
     *
     * @return The template.
     */
    String template() default Constants.DEFAULT_JAVA_TEMPLATE;

    /**
     * Returns the template type.
     *
     * @return The type.
     */
    String type() default Constants.DEFAULT_JAVA_TYPE;
}
