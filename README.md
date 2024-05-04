# Semantic Version Annotation Processor

[![License (3-Clause BSD)](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg?style=flat-square)](https://opensource.org/licenses/BSD-3-Clause)
[![Java](https://img.shields.io/badge/java-17%2B-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![bld](https://img.shields.io/badge/1.9.1-FA9052?label=bld&labelColor=2392FF)](https://rife2.com/bld)
[![release](https://img.shields.io/github/release/ethauvin/semver.svg)](https://github.com/ethauvin/semver/releases/latest)
[![Nexus Snapshot](https://img.shields.io/nexus/s/net.thauvin.erik/semver?label=snapshot&server=https%3A%2F%2Foss.sonatype.org%2F)](https://oss.sonatype.org/content/repositories/snapshots/net/thauvin/erik/semver/)
[![Maven Central](https://img.shields.io/maven-central/v/net.thauvin.erik/semver.svg)](https://central.sonatype.com/artifact/net.thauvin.erik/semver)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ethauvin_semver&metric=alert_status)](https://sonarcloud.io/dashboard?id=ethauvin_semver)
[![GitHub CI](https://github.com/ethauvin/semver/actions/workflows/gradle.yml/badge.svg)](https://github.com/ethauvin/semver/actions/workflows/gradle.yml)
[![Build status](https://ci.appveyor.com/api/projects/status/nbv4mxd1gpxtx69o?svg=true)](https://ci.appveyor.com/project/ethauvin/semver)
[![CircleCI](https://circleci.com/gh/ethauvin/semver/tree/master.svg?style=shield)](https://circleci.com/gh/ethauvin/semver/tree/master)

An [annotation processor](https://docs.oracle.com/javase/8/docs/api/javax/annotation/processing/Processor.html) that automatically generates a `GeneratedVersion` class based on a [Mustache](https://mustache.github.io/) template and containing the [semantic version](https://semver.org/) (major, minor, patch, etc.) that is read from a [Properties](https://docs.oracle.com/javase/tutorial/essential/environment/properties.html) file or defined in the [annotation](https://docs.oracle.com/javase/tutorial/java/annotations/basics.html).

This processor was inspired by Cédric Beust's [version-processor](https://github.com/cbeust/version-processor) and works well in conjunction with the [__Semantic Version Plugin for Gradle__](https://github.com/ethauvin/semver-gradle).

## Table of Contents

- [Examples](#examples)
- [Template](#template)
  - [Default Template](#default-template)
  - [Custom Template](#custom-template)
- [Elements & Properties](#elements--properties)
- [Maven](#maven)
- [bld](#bld)
- [Gradle](#gradle)
  - [Class Generation](#class-generation)
  - [Class & Source Generation](#class--source-generation)
- [Kotlin](#kotlin)
  - [Kotlin & Gradle](#kotlin--gradle)
- [Auto-Increment](#auto-increment)
- [Contributing](#contributing)

## Examples

- Using annotation elements:

```java
import net.thauvin.erik.semver.Version;

@Version(major = 2, minor = 1, patch = 1, preRelease = "beta")
public class A {
    // ...
}
```

- Or using a [properties](hhttps://github.com/ethauvin/semver/blob/master/examples/java/version.properties) file:

```java
import net.thauvin.erik.semver.Version;

@Version(properties = "version.properties")
public class A {
    // ...
}
```

```ini
# version.properties
version.major=1
version.minor=0
version.patch=0
version.prerelease=beta
```

[View Examples](https://github.com/ethauvin/semver/tree/master/examples)

## Template

Upon running the annotation processor, a source file [GeneratedVersion.java](https://github.com/ethauvin/semver/blob/master/examples/java/src/generated/java/com/example/GeneratedVersion.java) is automatically generated with static methods to access the semantic version data. The source is based on a fully customizable Mustache [template](https://github.com/ethauvin/semver/blob/master/src/main/resources/semver.mustache).

To use your own template, simply create a `version.mustache` file in the project's root directory. The processor will automatically look for it.

To specify your own template name, use:

```java
@Version(template = "version.mustache")
public class A {
    // ...
}
```

### Default Template

The [default template](https://github.com/ethauvin/semver/blob/master/src/main/resources/semver.mustache) implements the following static variables:

| Field              | Description                      | Example                                                                           |
|:-------------------|:---------------------------------|:----------------------------------------------------------------------------------|
| `PROJECT`          | The project name, if any.        | `MyProject`                                                                       |
| `BUILDDATE`        | The build date.                  | [`java.util.Date`](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) |
| `VERSION`          | The full version string.         | `1.2.3-alpha+001`                                                                 |
| `MAJOR`            | The major version.               | `1`                                                                               |
| `MINOR`            | The minor version.               | `2`                                                                               |
| `PATCH`            | The patch version.               | `3`                                                                               |
| `PRERELEASE`       | The pre-release version, if any. | `alpha`                                                                           |
| `PRERELASE_PREFIX` | The pre-release prefix           | `-`                                                                               |
| `BUILDMETA`        | The build metadata, if any.      | `001`                                                                             |
| `BUILDMETA_PREFIX` | The metadata prefix.             | `+`                                                                               |
| `SEPARATOR`        | The version separator.           | `.`                                                                               |

### Custom Template

A very simple custom template might look something like:

```java
/* version.mustache */
package {{packageName}};

import java.util.Date;

public final class {{className}} {
    public final static String PROJECT = "{{project}}";
    public final static Date DATE = new Date({{epoch}}L);
    public final static String VERSION = "{{semver}}";
}
```

The mustache variables automatically filled in by the processor are:

| Variable                      | Description                 | Type     |
|:------------------------------|:----------------------------|:---------|
| `{{packageName}}`             | The package name.           | `String` |
| `{{className}}`               | The class name.             | `String` |
| `{{project}}`                 | The project name.           | `String` |
| `{{epoch}}`                   | The build epoch/unix time.  | `long`   |
| `{{major}}`                   | The major version.          | `int`    |
| `{{minor}}`                   | The minor version.          | `int`    |
| `{{patch}}`                   | The patch version.          | `int`    |
| `{{preRelease}}`              | The pre-release version.    | `String` |
| `{{preReleasePrefix}}`        | The pre-release prefix.     | `String` |
| `{{buildMeta}}`               | The build metadata version. | `String` |
| `{{buildMetaPrefix}}`         | The metadata prefix.        | `String` |
| `{{separator}}`               | The version separator.      | `String` |
| `{{semver}}` or `{{version}}` | The full semantic version.  | `String` |

## Elements & Properties

The following annotation elements and properties are available:

| Element            | Property                    | Description                       | Default                   |
|:-------------------|:----------------------------|:----------------------------------|:--------------------------|
| `project`          | `version.project`           | The project name.                 |                           |
| `major`            | `version.major`             | The major version number.         | `1`                       |
| `minor`            | `version.major`             | The minor version number.         | `0`                       |
| `patch`            | `version.patch`             | The patch version number.         | `0`                       |
| `preRelease`       | `version.prerelease`        | The pre-release version.          |                           |
| `preReleasePrefix` | `version.prerelease.prefix` | The pre-release prefix.           | `-`                       |
| `buildMeta`        | `version.buildmeta`         | The build metadata version.       |                           |
| `buildMetaPrefix`  | `version.buildmeta.prefix`  | The metadata prefix.              | `+`                       |
| `separator`        | `version.separator`         | The version separator.            | `.`                       |
| `packageName`      |                             | The package name.                 | _Same as annotated class_ |
| `className`        |                             | The name of the generated class.  | `GeneratedVersion`        |
| `properties`       |                             | The properties file.              |                           |
| `template`         |                             | The template file.                | `version.mustache`        |
| `type`             |                             | Either `java` or `kt` for Kotlin. | `java`                    |
| `keysPrefix`       |                             | The prefix for all property keys. | `version.`                |

In order to easily incorporate with existing projects, the property keys may be assigned custom values:

```java
@Version(
  properties = "example.properties",
  keysPrefix = "example.",
  majorKey = "maj",
  minorKey = "min",
  patchKey = "build",
  preReleaseKey = "rel",
  buildMetaKey = "meta",
  projectKey = "project"
)
public class Example {
    // ...
}
```

```ini
# example.properties
example.project=Example
example.maj=1
example.min=0
example.build=0
example.rel=beta
example.meta=
# ...
```

> :warning: `keysPrefix` is a new element staring in `1.1.0` and may break older versions when using custom property keys.\
> :zap: A quick fix is to include `keysPrefix=""` in the annotation to remove the default `version.` prefix.

## Maven

To install and run from [Maven](https://maven.apache.org/), configure an artifact as follows:

```xml
<dependency>
    <groupId>net.thauvin.erik</groupId>
    <artifactId>semver</artifactId>
    <version>1.2.1</version>
</dependency>
```

Please look at [pom.xml](https://github.com/ethauvin/semver/blob/master/examples/java/pom.xml) in the [examples/java](https://github.com/ethauvin/semver/tree/master/examples/java) directory for a sample:

```bash
mvn verify
```

## bld

To install and run from [bld](https://rife2.com/bld), just add the dependency to your build file:

```java
public class ExampleBuild extends Project {
    public ExampleBuild() {
        // ...
        scope(compile)
            .include(dependency("net.thauvin.erik", "semver", version(1, 2, 1)));
    }
}
```

Please look at [ExampleBuild](https://github.com/ethauvin/semver/blob/master/examples/java/bld/src/bld/java/com/example/ExampleBuild.java) in the [examples/java/bld](https://github.com/ethauvin/semver/tree/master/examples/java/bld) directory for a sample. It also shows how to incorporate the generated code into the `source tree`, more information is also available [here](https://forum.uwyn.com/post/36).

bld also has a [Generated Version](https://github.com/rife2/bld-generated-version) extension which provides similar functionalities.

## Gradle

### Class Generation

To install and run from [Gradle](https://gradle.org/), add the following to [build.gradle](https://github.com/ethauvin/semver/blob/master/examples/java/gradle/build.gradle):

```gradle
repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor 'net.thauvin.erik:semver:1.2.1'
    compileOnly 'net.thauvin.erik:semver:1.2.1'
}

tasks.withType(JavaCompile).configureEach {
    options.compilerArgs += [ "-Asemver.project.dir=$projectDir" ]
}
```

The directory containing the configuration files (`version.properties`, `version.mustache`) must be specified using the `semver.project.dir` processor argument.

The [`GeneratedVersion.java`](https://github.com/ethauvin/semver/blob/master/examples/java/src/generated/java/com/example/GeneratedVersion.java) class will be automatically created in the `build/generated` directory upon compiling.

Please look at [build.gradle](https://github.com/ethauvin/semver/blob/master/examples/java/gradle/build.gradle) in the [examples/java/gradle](https://github.com/ethauvin/semver/tree/master/examples/java/gradle) directory for a sample.

### Class & Source Generation

In order to also incorporate the generated source code into the `source tree`, add the following to [build.gradle](https://github.com/ethauvin/semver/blob/master/examples/java/build.gradle):

```gradle
tasks.withType(JavaCompile).configureEach {
    options.generatedSourceOutputDirectory.set(file("${projectDir}/src/generated/java"))
}
```

The [`GeneratedVersion.java`](https://github.com/ethauvin/semver/blob/master/examples/java/src/generated/java/com/example/GeneratedVersion.java) file will now be located in `src/generated`.

## Kotlin

The annotation processor also supports [Kotlin](https://kotlinlang.org/).

To generate a Kotlin version file, simply specify the `type` as follows:

```kotlin
import net.thauvin.erik.semver.Version

@Version(properties = "version.properties", type="kt")
open class Main {
    // ...
}
```

The [Kotlin default template](https://github.com/ethauvin/semver/blob/master/src/main/resources/semver-kt.mustache) implements the same static fields and functions as the [Java template](#default-template).

Please look at the [examples/kotlin](https://github.com/ethauvin/semver/tree/master/examples/kotlin) project for a [build.gradle.kts](https://github.com/ethauvin/semver/blob/master/examples/kotlin/build.gradle.kts) sample.

### Kotlin & Gradle

To install and run from [Gradle](https://gradle.org/), add the following to [build.gradle.kts](https://github.com/ethauvin/semver/blob/master/examples/kotlin/build.gradle.kts):

```kotlin
var semverProcessor = "net.thauvin.erik:semver:1.2.1"

dependencies {
    kapt(semverProcessor)
    compileOnly(semverProcessor)
}

kapt {
    arguments {
        arg("semver.project.dir", projectDir)
    }
}
```

The directory containing the configuration files (`version.properties`, `version.mustache`) must be specified using the `semver.project.dir` processor argument.

## Auto-Increment

Incrementing the version is best left to your favorite build system. For a solution using Gradle, please have a look at the [__Semver Version Plugin for Gradle__](https://github.com/ethauvin/semver-gradle).

There are also full [examples](https://github.com/ethauvin/semver-gradle/tree/master/examples/annotation-processor) in both [Java](https://github.com/ethauvin/semver-gradle/tree/master/examples/annotation-processor/java) and [Kotlin](https://github.com/ethauvin/semver-gradle/tree/master/examples/annotation-processor/kotlin) showing how to use both the plugin and annotation processor concurrently.

## Contributing

If you want to contribute to this project, all you have to do is clone the GitHub
repository:

```console
git clone git@github.com:ethauvin/semver.git
```

Then use [bld](https://rife2.com/bld) to build:

```console
cd semver
./bld compile
```

The project has an [IntelliJ IDEA](https://www.jetbrains.com/idea/) project structure. You can just open it after all
the dependencies were downloaded and peruse the code.
