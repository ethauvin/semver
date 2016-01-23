/*
 * VersionInfo.java
 *
 * Copyright (c) 2016, Erik C. Thauvin (erik@thauvin.net)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the author nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.thauvin.erik.semver;

/**
 * The <code>VersionInfo</code> class.
 *
 * @author <a href="mailto:erik@thauvin.net">Erik C. Thauvin</a>
 * @created 2016-01-16
 * @since 1.0
 */
public class VersionInfo
{
	private final long epoch = System.currentTimeMillis();

	private String buildmeta;

	private int major;

	private int minor;

	private int patch;

	private String prerelease;

	private String project;

	public VersionInfo()
	{
		major = Constants.DEFAULT_MAJOR;
		minor = Constants.DEFAULT_MINOR;
		patch = Constants.DEFAULT_PATCH;
		buildmeta = Constants.EMPTY;
		prerelease = Constants.EMPTY;
		project = Constants.EMPTY;
	}

	public VersionInfo(final Version version)
	{
		major = version.major();
		minor = version.minor();
		patch = version.patch();
		buildmeta = version.buildmeta();
		prerelease = version.prerelease();
		project = version.project();
	}

	public String getBuildMetadata()
	{
		return buildmeta;
	}

	public void setBuildMetadata(final String buildmeta)
	{
		this.buildmeta = buildmeta;
	}

	public long getEpoch()
	{
		return epoch;
	}

	public int getMajor()
	{
		return major;
	}

	public void setMajor(final int major)
	{
		this.major = major;
	}

	public int getMinor()
	{
		return minor;
	}

	public void setMinor(final int minor)
	{
		this.minor = minor;
	}

	public int getPatch()
	{
		return patch;
	}

	public void setPatch(final int patch)
	{
		this.patch = patch;
	}

	public String getPreRelease()
	{
		return prerelease;
	}

	public void setPreRelease(final String prerelease)
	{
		this.prerelease = prerelease;
	}

	public String getProject()
	{
		return project;
	}

	public void setProject(String project)
	{
		this.project = project;
	}

	public String getVersion()
	{
		return "" + major + '.' + minor + '.' + patch + (prerelease.length() > 0 ? '-' + prerelease : "") + (
				buildmeta.length() > 0 ? '+' + buildmeta : "");
	}
}