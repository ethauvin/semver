/*
 * VersionTest.java
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

import java.lang.annotation.Annotation;

/**
 * The <code>VersionTest</code> class.
 *
 * @author <a href="https://erik.thauvin.net/" target="_blank">Erik C. Thauvin</a>
 * @created 2019-04-02
 * @since 1.2.0
 */
@SuppressWarnings({"ClassExplicitlyAnnotation"})
class VersionTest implements Version {
    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public String buildMeta() {
        return "007";
    }

    @Override
    public String buildMetaKey() {
        return "meta";
    }

    @Override
    public String buildMetaPrefix() {
        return "+";
    }

    @Override
    public String buildMetaPrefixKey() {
        return "meta.prefix";
    }

    @Override
    public String className() {
        return "MyTest";
    }

    @Override
    public String keysPrefix() {
        return "build.";
    }

    @Override
    public int major() {
        return 2;
    }

    @Override
    public String majorKey() {
        return "major";
    }

    @Override
    public int minor() {
        return 17;
    }

    @Override
    public String minorKey() {
        return "minor";
    }

    @Override
    public String packageName() {
        return "com.foo.example";
    }

    @Override
    public int patch() {
        return 52;
    }

    @Override
    public String patchKey() {
        return "patch";
    }

    @Override
    public String preRelease() {
        return "beta";
    }

    @Override
    public String preReleaseKey() {
        return "prerelease";
    }

    @Override
    public String preReleasePrefix() {
        return "-";
    }

    @Override
    public String preReleasePrefixKey() {
        return "prerelease.prefix";
    }

    @Override
    public String project() {
        return "My Test Project";
    }

    @Override
    public String projectKey() {
        return "project";
    }

    @Override
    public String properties() {
        return "test.properties";
    }

    @Override
    public String separator() {
        return ".";
    }

    @Override
    public String separatorKey() {
        return "separator";
    }

    @Override
    public String template() {
        return "myversion.mustache";
    }

    @Override
    public String type() {
        return "kt";
    }
}
