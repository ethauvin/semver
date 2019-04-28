/*
 * VersionInfo.java
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

/**
 * The <code>VersionInfo</code> class is used to hold and retrieve the semantic version values.
 *
 * @author <a href="mailto:erik@thauvin.net" target="_blank">Erik C. Thauvin</a>
 * @created 2016-01-16
 * @since 1.0
 */
@SuppressWarnings("PMD.DataClass")
public class VersionInfo {
    private final long epoch = System.currentTimeMillis();

    private String buildMeta;
    private String buildMetaPrefix;
    private String className;
    private int major;
    private int minor;
    private String packageName;
    private int patch;
    private String preRelease;
    private String preReleasePrefix;
    private String project;
    private String separator;


    /**
     * Creates a new object with default values.
     */
    public VersionInfo() {
        buildMeta = Constants.EMPTY;
        buildMetaPrefix = Constants.DEFAULT_BUILDMETA_PREFIX;
        className = Constants.DEFAULT_CLASS_NAME;
        minor = Constants.DEFAULT_MINOR;
        packageName = Constants.EMPTY;
        patch = Constants.DEFAULT_PATCH;
        preRelease = Constants.EMPTY;
        preReleasePrefix = Constants.DEFAULT_PRERELEASE_PREFIX;
        project = Constants.EMPTY;
        separator = Constants.DEFAULT_SEPARATOR;
        major = Constants.DEFAULT_MAJOR;
    }

    /**
     * Creates a new object with values from a {@link net.thauvin.erik.semver.VersionInfo VersionInfo} object.
     *
     * @param version The version object.
     */
    public VersionInfo(final Version version) {
        buildMeta = version.buildMeta();
        buildMetaPrefix = version.buildMetaPrefix();
        className = version.className();
        major = version.major();
        minor = version.minor();
        packageName = version.packageName();
        patch = version.patch();
        preRelease = version.preRelease();
        preReleasePrefix = version.preReleasePrefix();
        project = version.project();
        separator = version.separator();
    }

    /**
     * Returns the build meta-data.
     *
     * @return The build meta-data.
     */
    public String getBuildMeta() {
        return buildMeta;
    }

    /**
     * Sets the build meta-data.
     *
     * @param buildMeta The new build meta-data.
     */
    public void setBuildMeta(final String buildMeta) {
        this.buildMeta = buildMeta;
    }

    /**
     * Returns the meta-data prefix.
     *
     * @return The meta-data prefix.
     */
    public String getBuildMetaPrefix() {
        return buildMetaPrefix;
    }

    /**
     * Sets the meta-data prefix.
     *
     * @param buildMetaPrefix The meta-data prefix.
     */
    public void setBuildMetaPrefix(final String buildMetaPrefix) {
        this.buildMetaPrefix = buildMetaPrefix;
    }

    /**
     * Returns the class name.
     *
     * @return The class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the class name.
     *
     * @param className The new class name.
     */
    public void setClassName(final String className) {
        this.className = className;
    }

    /**
     * Returns the build epoch/Unix timestamp.
     *
     * @return The build epoch.
     */
    public long getEpoch() {
        return epoch;
    }

    /**
     * Returns the major version.
     *
     * @return The major version.
     */
    public int getMajor() {
        return major;
    }

    /**
     * Sets the major version.
     *
     * @param major The new major version.
     */
    public void setMajor(final int major) {
        this.major = major;
    }

    /**
     * Returns the major version.
     *
     * @return The major version.
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Sets the minor version.
     *
     * @param minor The new minor version.
     */
    public void setMinor(final int minor) {
        this.minor = minor;
    }

    /**
     * Returns the package name.
     *
     * @return The package name.
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Sets the package name.
     *
     * @param packageName The new package name.
     */
    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    /**
     * Returns the patch version.
     *
     * @return The patch version.
     */
    public int getPatch() {
        return patch;
    }

    /**
     * Sets the patch version.
     *
     * @param patch The new patch version.
     */
    public void setPatch(final int patch) {
        this.patch = patch;
    }

    /**
     * Returns the pre-release version.
     *
     * @return The pre-release version.
     */
    public String getPreRelease() {
        return preRelease;
    }

    /**
     * Sets the pre-release version.
     *
     * @param preRelease The new pre-release version.
     */
    public void setPreRelease(final String preRelease) {
        this.preRelease = preRelease;
    }

    /**
     * Returns the pre-release prefix.
     *
     * @return The pre-release prefix.
     */
    public String getPreReleasePrefix() {
        return preReleasePrefix;
    }

    /**
     * Sets the pre-release prefix.
     *
     * @param preReleasePrefix The new pre-release prefix.
     */
    public void setPreReleasePrefix(final String preReleasePrefix) {
        this.preReleasePrefix = preReleasePrefix;
    }

    /**
     * Returns the project name.
     *
     * @return The project name.
     */
    public String getProject() {
        return project;
    }

    /**
     * Sets the project name.
     *
     * @param project The new project name.
     */
    public void setProject(final String project) {
        this.project = project;
    }

    /**
     * Sames as {@link #getVersion()}.
     */
    public String getSemver() {
        return getVersion();
    }

    /**
     * Returns the version separator.
     *
     * @return The version separator.
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Sets the version separator.
     *
     * @param separator The new version separator.
     */
    public void setSeparator(final String separator) {
        this.separator = separator;
    }

    /**
     * Returns the full version string.
     *
     * <p>Formatted as:
     * <blockquote>
     * <code>
     * [MAJOR][SEPARATOR][MINOR][SEPARATOR][PATCH][[PRERELEASE-PREFIX][PRERELEASE]][[BUILDMETA-PREFIX][BUILDMETA]]
     * </code>
     * </blockquote>
     *
     * <p>For example:
     * <ul>
     * <li><code>1.0.0</code></li>
     * <li><code>1.0.0-beta</code></li>
     * <li><code>1.0.0+20160124144700</code></li>
     * <li><code>1.0.0-alpha+001</code></li>
     * </ul>
     *
     * @return The version string.
     */
    public String getVersion() {
        return major
            + separator
            + minor
            + separator
            + patch
            + (preRelease.length() > 0 ? preReleasePrefix + preRelease : "")
            + (buildMeta.length() > 0 ? buildMetaPrefix + buildMeta : "");
    }
}
