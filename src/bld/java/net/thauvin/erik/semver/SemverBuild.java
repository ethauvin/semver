/*
 * SemverBuild.java
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

import rife.bld.BuildCommand;
import rife.bld.Project;
import rife.bld.extension.ExecOperation;
import rife.bld.extension.JacocoReportOperation;
import rife.bld.extension.PmdOperation;
import rife.bld.publish.*;
import rife.tools.exceptions.FileUtilsErrorException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.compile;
import static rife.bld.dependencies.Scope.test;
import static rife.bld.operations.JavadocOptions.DocLinkOption.NO_MISSING;

public class SemverBuild extends Project {
    public SemverBuild() {
        pkg = "net.thauvin.erik";
        name = "SemVer";
        version = version(1, 2, 1, "SNAPSHOT");

        var description = "Semantic Version Annotation Processor";
        var url = "https://github.com/ethauvin/semver";

        javaRelease = 17;

        downloadSources = true;
        repositories = List.of(MAVEN_CENTRAL, SONATYPE_SNAPSHOTS);

        scope(compile)
                .include(dependency("com.github.spullara.mustache.java", "compiler",
                        version(0, 9, 10)));
        scope(test)
                .include(dependency("org.junit.jupiter", "junit-jupiter", version(5, 10, 0)))
                .include(dependency("org.junit.platform", "junit-platform-console-standalone", version(1, 10, 0)));


        javadocOperation().javadocOptions()
                .tag("created.on", "a", "Created on:")
                .windowTitle(name + ' ' + version.toString() + " API")
                .docLint(NO_MISSING);

        publishOperation()
                .repository(version.isSnapshot() ? repository(SONATYPE_SNAPSHOTS_LEGACY.location())
                        .withCredentials(property("sonatype.user"), property("sonatype.password"))
                        : repository(SONATYPE_RELEASES.location())
                        .withCredentials(property("sonatype.user"), property("sonatype.password")))
                .info(new PublishInfo()
                        .groupId(pkg)
                        .artifactId(name.toLowerCase())
                        .name(name)
                        .version(version)
                        .description(description)
                        .url(url)
                        .developer(new PublishDeveloper()
                                .id("ethauvin")
                                .name("Erik C. Thauvin")
                                .email("erik@thauvin.net")
                                .url("https://erik.thauvin.net/"))
                        .license(new PublishLicense()
                                .name("The BSD 3-Clause License")
                                .url("http://opensource.org/licenses/BSD-3-Clause"))
                        .scm(new PublishScm()
                                .connection("scm:git:" + url + ".git")
                                .developerConnection("scm:git:git@github.com:ethauvin/" + name.toLowerCase() + ".git")
                                .url(url))
                        .signKey(property("sign.key"))
                        .signPassphrase(property("sign.passphrase")));
    }

    public static void main(String[] args) {
        new SemverBuild().start(args);
    }

    @BuildCommand(summary = "Generates JaCoCo Reports")
    public void jacoco() throws IOException {
        new JacocoReportOperation().fromProject(this).execute();
    }

    @BuildCommand(summary = "Build the docs with Pandoc")
    public void pandoc() throws Exception {
        new ExecOperation()
                .fromProject(this)
                .command("pandoc",
                        "--from", "gfm",
                        "--to", "html5",
                        "--metadata", "pagetitle=Semantic Version Annotation Processor",
                        "-s",
                        "-c", "docs/github-pandoc.css",
                        "-o", "docs/README.html",
                        "README.md")
                .execute();

    }

    @BuildCommand(summary = "Runs PMD analysis")
    public void pmd() {
        new PmdOperation()
                .fromProject(this)
                .failOnViolation(true)
                .ruleSets("config/pmd.xml")
                .execute();
    }

    @Override
    public void publish() throws Exception {
        super.publish();
        rootPom();
    }

    @Override
    public void publishLocal() throws Exception {
        super.publishLocal();
        rootPom();
    }

    @BuildCommand(value = "pom-root", summary = "Generates the POM file in the root directory")
    public void rootPom() throws FileUtilsErrorException {
        PomBuilder.generateInto(publishOperation().info(), dependencies(),
                Path.of(workDirectory.getPath(), "pom.xml").toFile());
    }
}
