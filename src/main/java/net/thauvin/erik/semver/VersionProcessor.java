/*
 * VersionProcessor.java
 *
 * Copyright (c) 2016-2022, Erik C. Thauvin (erik@thauvin.net)
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

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.MustacheNotFoundException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * The <code>VersionProcessor</code> class implements a semantic version annotation processor.
 *
 * @author <a href="mailto:erik@thauvin.net" target="_blank">Erik C. Thauvin</a>
 * @created 2016-01-13
 * @since 1.0
 */
@SuppressWarnings({"PMD.GuardLogStatement", "PMD.BeanMembersShouldSerialize"})
@SupportedOptions({Constants.KAPT_KOTLIN_GENERATED_OPTION_NAME, Constants.SEMVER_PROJECT_DIR_ARG})
public class VersionProcessor extends AbstractProcessor {
    private Filer filer;

    private Messager messager;

    private void error(final String s) {
        log(Diagnostic.Kind.ERROR, s);
    }

    private void error(final String s, final Throwable t) {
        log(Diagnostic.Kind.ERROR, t != null ? t.toString() : s);
    }

    @SuppressFBWarnings({"PATH_TRAVERSAL_IN"})
    private VersionInfo findValues(final Version version) throws IOException {
        final VersionInfo versionInfo = new VersionInfo(version);

        if (version.properties().length() > 0) {
            final File propsFile = getLocalFile(version.properties());
            if (propsFile.isFile() && propsFile.canRead()) {
                note("Found properties: " + propsFile.getName() + " (" + propsFile.getAbsoluteFile().getParent() + ')');

                final Properties p = new Properties();

                try (InputStreamReader reader = new InputStreamReader(
                        Files.newInputStream(propsFile.toPath()), StandardCharsets.UTF_8)) {
                    p.load(reader);

                    versionInfo.setProject(
                            p.getProperty(version.keysPrefix() + version.projectKey(), version.project()));
                    versionInfo.setMajor(
                            parseIntProperty(p, version.keysPrefix() + version.majorKey(), version.major()));
                    versionInfo.setMinor(
                            parseIntProperty(p, version.keysPrefix() + version.minorKey(), version.minor()));
                    versionInfo.setPatch(
                            parseIntProperty(p, version.keysPrefix() + version.patchKey(), version.patch()));
                    versionInfo.setBuildMeta(
                            p.getProperty(version.keysPrefix() + version.buildMetaKey(), version.buildMeta()));
                    versionInfo.setBuildMetaPrefix(
                            p.getProperty(version.keysPrefix() + version.buildMetaPrefixKey(), version.buildMetaPrefix()));
                    versionInfo.setPreRelease(
                            p.getProperty(version.keysPrefix() + version.preReleaseKey(), version.preRelease()));
                    versionInfo.setPreReleasePrefix(p.getProperty(version.keysPrefix() + version.preReleasePrefixKey(),
                            version.preReleasePrefix()));
                    versionInfo.setSeparator(
                            p.getProperty(version.keysPrefix() + version.separatorKey(), version.separator()));
                }
            } else {
                final String findOrRead;
                if (propsFile.canRead()) {
                    findOrRead = "find";
                } else {
                    findOrRead = "read";
                }
                error("Could not " + findOrRead + ": " + propsFile);
                throw new FileNotFoundException(
                        "Could not " + findOrRead + " the specified file: `" + propsFile.getAbsolutePath() + '`');
            }
        }

        return versionInfo;
    }

    @SuppressFBWarnings("PATH_TRAVERSAL_IN")
    private File getLocalFile(final String fileName) {
        if (processingEnv != null) { // null when testing.
            final String prop = processingEnv.getOptions().get(Constants.SEMVER_PROJECT_DIR_ARG);
            if (prop != null) {
                return new File(prop, fileName);
            }
        }
        return new File(new File("").getAbsolutePath(), fileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> result = new HashSet<>();
        result.add(Version.class.getCanonicalName());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        final boolean isLocalTemplate = getLocalFile(Constants.DEFAULT_TEMPLATE_NAME).exists();
        for (final Element element : roundEnv.getElementsAnnotatedWith(Version.class)) {
            final Version version = element.getAnnotation(Version.class);
            if (element.getKind() == ElementKind.CLASS) {
                final Element enclosingElement = element.getEnclosingElement();
                if (enclosingElement.getKind() == ElementKind.PACKAGE) {
                    final PackageElement packageElement = (PackageElement) enclosingElement;
                    try {
                        final VersionInfo versionInfo = findValues(version);
                        if (Constants.EMPTY.equals(version.packageName())) {
                            versionInfo.setPackageName(packageElement.getQualifiedName().toString());
                        }
                        note("Found version: " + versionInfo.getVersion());
                        final String template;
                        if (isLocalTemplate && Constants.DEFAULT_JAVA_TEMPLATE.equals(version.template())) {
                            template = Constants.DEFAULT_TEMPLATE_NAME;
                        } else if (Constants.DEFAULT_JAVA_TEMPLATE.equals(version.template()) && Constants.KOTLIN_TYPE
                                .equals(version.type())) {
                            template = Constants.DEFAULT_KOTLIN_TEMPLATE;
                        } else {
                            template = version.template();
                        }

                        writeTemplate(version.type(), versionInfo, template);
                    } catch (IOException | MustacheNotFoundException e) {
                        error("An error occurred while running the annotation processor: " + e.getMessage(), e);
                    }
                }
            }
        }
        return true;
    }

    private void log(final Diagnostic.Kind kind, final String s) {
        if (messager != null) {
            messager.printMessage(kind,
                    '[' + VersionProcessor.class.getSimpleName() + "] " + s + System.lineSeparator());
        }
    }

    private void note(final String s) {
        log(Diagnostic.Kind.NOTE, s);
    }

    private int parseIntProperty(final Properties p, final String key, final int defaultValue) {
        try {
            return Math.abs(Integer.parseInt(p.getProperty(key, Integer.toString(defaultValue)).trim()));
        } catch (NumberFormatException ignore) {
            warn("Invalid property value: " + key);
            return defaultValue;
        }
    }

    private void warn(final String s) {
        log(Diagnostic.Kind.WARNING, s);
    }

    @SuppressFBWarnings({"PATH_TRAVERSAL_IN", "UAC_UNNECESSARY_API_CONVERSION_FILE_TO_PATH"})
    private void writeTemplate(final String type, final VersionInfo versionInfo, final String template)
            throws IOException {
        final File dir = getLocalFile("");
        final MustacheFactory mf = new DefaultMustacheFactory(dir);
        final Mustache mustache = mf.compile(template);

        final String templateName;
        switch (mustache.getName()) {
            case Constants.DEFAULT_JAVA_TEMPLATE:
                templateName = "default (Java)";
                break;
            case Constants.DEFAULT_KOTLIN_TEMPLATE:
                templateName = "default (Kotlin)";
                break;
            default:
                templateName = mustache.getName() + " (" + dir.getAbsolutePath() + ')';
                break;
        }
        note("Loaded template: " + templateName);

        final String fileName = versionInfo.getClassName() + '.' + type;
        if (Constants.KOTLIN_TYPE.equalsIgnoreCase(type)) {
            final String kaptGenDir = processingEnv.getOptions().get(Constants.KAPT_KOTLIN_GENERATED_OPTION_NAME);
            if (kaptGenDir == null) {
                throw new IOException("Could not find the target directory for generated Kotlin files.");
            }
            final File ktFile = new File(kaptGenDir, fileName);
            if (!ktFile.getParentFile().exists() && !ktFile.getParentFile().mkdirs()) {
                note("Could not create target directory: " + ktFile.getParentFile().getAbsolutePath());
            }
            try (OutputStreamWriter osw = new OutputStreamWriter(Files.newOutputStream(ktFile.toPath()),
                    StandardCharsets.UTF_8)) {
                mustache.execute(osw, versionInfo).flush();
            }
            note("Generated source: " + fileName + " (" + ktFile.getParentFile().getAbsolutePath() + ')');
        } else {
            final FileObject jfo = filer.createSourceFile(
                    versionInfo.getPackageName() + '.' + versionInfo.getClassName());
            try (Writer writer = jfo.openWriter()) {
                mustache.execute(writer, versionInfo).flush();
            }
            note("Generated source: " + fileName + " (" + new File(jfo.getName()).getAbsoluteFile().getParent() + ')');
        }
    }
}
