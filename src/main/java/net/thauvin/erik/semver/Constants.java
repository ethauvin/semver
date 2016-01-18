/*
 * Constants.java
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
 * The <code>Constants</code> class.
 *
 * @author <a href="mailto:erik@thauvin.net">Erik C. Thauvin</a>
 * @created 2016-01-13
 * @since 1.0
 */
public final class Constants
{
	public static final String DEFAULT_CLASS_NAME = "GeneratedVersion";

	public static final int DEFAULT_MAJOR = 1;

	public static final int DEFAULT_MINOR = 0;

	public static final int DEFAULT_PATCH = 0;

	public static final String DEFAULT_TEMPLATE = "version.vm";

	public static final String EMPTY = "";

	public static final String KEY_VERSION_BUILDMETA = "version.buildmeta";

	public static final String KEY_VERSION_MAJOR = "version.major";

	public static final String KEY_VERSION_MINOR = "version.minor";

	public static final String KEY_VERSION_PATCH = "version.patch";

	public static final String KEY_VERSION_PRERELEASE = "version.prerelease";

	public static final String VELOCITY_PROPERTIES = "velocity.properties";

	/**
	 * Disables the default constructor.
	 *
	 * @throws UnsupportedOperationException if an error occurred. if the constructor is called.
	 */
	private Constants()
			throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Illegal constructor call.");
	}
}