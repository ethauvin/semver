/*
 * VersionProcessor.java
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

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * The <code>VersionProcessor</code> class.
 *
 * @author <a href="mailto:erik@thauvin.net">Erik C. Thauvin</a>
 * @created 2016-01-13
 * @since 1.0
 */
public class VersionProcessor extends AbstractProcessor
{
	private Filer filer;

	private Messager messager;

	private void error(final String s)
	{
		log(Diagnostic.Kind.ERROR, s);
	}

	private void error(final String s, final Throwable t)
	{
		messager.printMessage(Diagnostic.Kind.ERROR, (t != null ? t.toString() : s));
	}

	private VersionInfo findValues(final Version version)
			throws IOException
	{
		final VersionInfo versionInfo;

		if (version.properties().length() > 0)
		{
			versionInfo = new VersionInfo();

			final File propsFile = new File(version.properties());
			if (propsFile.exists())
			{
				note("Found properties: " + propsFile);
				final Properties p = new Properties();

				try (FileReader reader = new FileReader(propsFile))
				{
					p.load(reader);

					versionInfo.setMajor(parseIntProperty(p, version.majorKey(), Constants.DEFAULT_MAJOR));
					versionInfo.setMinor(parseIntProperty(p, version.minorKey(), Constants.DEFAULT_MINOR));
					versionInfo.setPatch(parseIntProperty(p, version.patchKey(), Constants.DEFAULT_PATCH));
					versionInfo.setBuildMetadata(p.getProperty(version.buildmetaKey(), Constants.EMPTY));
					versionInfo.setPreRelease(p.getProperty(version.prereleaseKey(), Constants.EMPTY));
				}
			}
			else
			{
				error("Could not find: " + propsFile);
				throw new FileNotFoundException(propsFile + " (The system cannot find the file specified)");
			}
		}
		else
		{
			versionInfo = new VersionInfo(version);
		}

		return versionInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getSupportedAnnotationTypes()
	{
		final Set<String> result = new HashSet<>();
		result.add(Version.class.getCanonicalName());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SourceVersion getSupportedSourceVersion()
	{
		return SourceVersion.RELEASE_8;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void init(final ProcessingEnvironment processingEnv)
	{
		super.init(processingEnv);
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv)
	{
		for (final Element annotatedElement : roundEnv.getElementsAnnotatedWith(Version.class))
		{
			final Version version = annotatedElement.getAnnotation(Version.class);
			if (annotatedElement.getKind() == ElementKind.CLASS)
			{
				final Element enclosing = annotatedElement.getEnclosingElement();
				if (enclosing.getKind() == ElementKind.PACKAGE)
				{
					final PackageElement packageElement = (PackageElement) enclosing;
					try
					{
						final VersionInfo versionInfo = findValues(version);
						note("Found version: " + versionInfo.getVersion());
						writeTemplate(packageElement.getQualifiedName().toString(),
						              version.className(),
						              versionInfo,
						              version.template());
					}
					catch (IOException e)
					{
						error("IOException occurred while running annotation processor", e);
					}
				}
			}
		}
		return true;
	}

	private void log(final Diagnostic.Kind kind, final String s)
	{
		messager.printMessage(kind, '[' + VersionProcessor.class.getSimpleName() + "] " + s);
	}

	private void note(final String s)
	{
		log(Diagnostic.Kind.NOTE, s);
	}

	private int parseIntProperty(final Properties p, final String property, final int defaultValue)
	{
		try
		{
			return Integer.parseInt(p.getProperty(property, "" + defaultValue));
		}
		catch (NumberFormatException ignore)
		{
			warn("Invalid property value: " + property);
			return defaultValue;
		}
	}

	private void warn(final String s)
	{
		log(Diagnostic.Kind.WARNING, s);
	}

	private void writeTemplate(final String packageName, final String className, final VersionInfo versionInfo,
	                           final String template)
			throws IOException
	{
		final Properties p = new Properties();
		final URL url = this.getClass().getClassLoader().getResource(Constants.VELOCITY_PROPERTIES);

		if (url != null)
		{
			p.load(url.openStream());

			final VelocityEngine ve = new VelocityEngine(p);
			ve.init();

			final VelocityContext vc = new VelocityContext();
			vc.put("packageName", packageName);
			vc.put("className", className);
			vc.put("buildmeta", versionInfo.getBuildMetadata());
			vc.put("epoch", versionInfo.getEpoch());
			vc.put("patch", versionInfo.getPatch());
			vc.put("major", versionInfo.getMajor());
			vc.put("minor", versionInfo.getMinor());
			vc.put("prerelease", versionInfo.getPreRelease());

			final Template vt = ve.getTemplate(template);

			note("Loaded template: " + vt.getName());

			final JavaFileObject jfo = filer.createSourceFile(packageName + '.' + className);
			try (final Writer writer = jfo.openWriter())
			{
				vt.merge(vc, writer);
			}

			note("Generated source: " + jfo.getName());
		}
		else
		{
			error("Could not load '" + Constants.VELOCITY_PROPERTIES + "' from jar.");
		}

	}
}