#  Semantic Version Annotation Processor

[![License (3-Clause BSD)](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg?style=flat-square)](http://opensource.org/licenses/BSD-3-Clause) [![release](https://img.shields.io/github/release/ethauvin/semver.svg)](https://github.com/ethauvin/semver/releases/latest) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.thauvin.erik/semver/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.thauvin.erik/semver) [ ![Download](https://api.bintray.com/packages/ethauvin/maven/SemVer/images/download.svg) ](https://bintray.com/ethauvin/maven/SemVer/_latestVersion)  
[![Known Vulnerabilities](https://snyk.io/test/github/ethauvin/semver/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/ethauvin/semver?targetFile=build.gradle) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ethauvin_semver&metric=alert_status)](https://sonarcloud.io/dashboard?id=ethauvin_semver) [![Build Status](https://travis-ci.org/ethauvin/semver.svg?branch=master)](https://travis-ci.org/ethauvin/semver) [![Build status](https://ci.appveyor.com/api/projects/status/nbv4mxd1gpxtx69o?svg=true)](https://ci.appveyor.com/project/ethauvin/semver) [![CircleCI](https://circleci.com/gh/ethauvin/semver/tree/master.svg?style=shield)](https://circleci.com/gh/ethauvin/semver/tree/master)

An [annotation processor](https://docs.oracle.com/javase/8/docs/api/javax/annotation/processing/Processor.html) that automatically generates a `GeneratedVersion` class based on a [Mustache](https://mustache.github.io/) template and containing the [semantic version](http://semver.org/) (major, minor, patch, etc.) that is read from a `Properties` file or defined in the [annotation](https://docs.oracle.com/javase/tutorial/java/annotations/basics.html).

This processor was inspired by Cédric Beust's [version-processor](https://github.com/cbeust/version-processor) and works well in conjunction with the [__Semantic Version Plugin for Gradle__](https://github.com/ethauvin/semver-gradle).

## Table of Contents
- [Examples](#examples)
- [Template](#template)
  - [Default Template](#default-template)
  - [Custom Template](#custom-template)
- [Elements & Properties](#elements--properties)
- [Usage with Maven, Gradle, Kotlin and Kobalt](#usage-with-maven-gradle-kotlin-and-kobalt)
  - [Maven](#maven)
  - [Gradle](#gradle)
    - [Class Generation](#class-generation)
    - [Class & Source Generation](#class--source-generation)
  - [Kotlin](#kotlin)
    - [Kotlin & Gradle](#kotlin--gradle)
  - [Kobalt](#kobalt)
- [Auto-Increment](#auto-increment)
  
## Examples

* Using annotation elements:

```java
import net.thauvin.erik.semver.Version;

@Version(major = 1, minor = 0, patch = 0, preRelease = "beta")
public class A {
// ...
```

* Or using a [properties](https://docs.oracle.com/javase/tutorial/essential/environment/properties.html) file:

```java
import net.thauvin.erik.semver.Version;

@Version(properties = "version.properties")
public class A {
// ...
```

```ini
# version.properties
version.major=1
version.minor=0
version.patch=0
version.prerelease=beta
```

[View Example](https://github.com/ethauvin/semver/tree/master/examples)

## Template

Upon running the annotation processor, a source file [`GeneratedVersion.java`](https://github.com/ethauvin/semver/blob/master/examples/java/src/generated/java/com/example/GeneratedVersion.java) is automatically generated with static methods to access the semantic version data. The source is based on a fully customizable [Mustache](https://mustache.github.io/) template.

To use your own template, simply create a `version.mustache` file in the project's root directory. The processor will automatically look for it.

To specify your own template name, use:

```java
@Version(template = "version.mustache")
public class A {
// ...
```

### Default Template

The [default template](https://github.com/ethauvin/semver/blob/master/src/main/resources/semver.mustache    ) implements the following static variables:

Field              | Description                      |  Example
:------------------|:---------------------------------|:-----------------
`PROJECT`          | The project name, if any.        | `MyProject`
`BUILDDATE`        | The build date.                  | [`java.util.Date`](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)
`VERSION`          | The full version string.         | `1.2.3-alpha+001`
`MAJOR`            | The major version.               | `1`
`MINOR`            | The minor version.               | `2`
`PATCH`            | The patch version.               | `3`
`PRERELEASE`       | The pre-release version, if any. | `alpha`
`PRERELASE_PREFIX` | The pre-release prefix           | `-`
`BUILDMETA`        | The build metadata, if any.      | `001`
`BUILDMETA_PREFIX` | The metadata prefix.             | `+`
`SEPARATOR`        | The version separator.           | `.`

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

Variable                      | Description                 | Type
:-----------------------------|:----------------------------|:--------
`{{packageName}}`             | The package name.           | `String`
`{{className}}`               | The class name.             | `String`
`{{project}}`                 | The project name.           | `String`
`{{epoch}}`                   | The build epoch/unix time.  | `long`
`{{major}}`                   | The major version.          | `int`
`{{minor}}`                   | The minor version.          | `int`
`{{patch}}`                   | The patch version.          | `int`
`{{preRelease}}`              | The pre-release version.    | `String`
`{{preReleasePrefix}}`        | The pre-release prefix.     | `String`
`{{buildMeta}}`               | The build metadata version. | `String`
`{{buildMetaPrefix}}`         | The metadata prefix.        | `String`
`{{separator}}`               | The version separator.      | `String`
`{{semver}}` or `{{version}}` | The full semantic version.  | `String`

Please also look at this [example](https://github.com/ethauvin/mobibot/blob/master/version.mustache) using [`java.time`](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html)

## Elements & Properties

The following annotation elements and properties are available:

Element            | Property                    | Description                       | Default
:------------------|:----------------------------|:----------------------------------|:-------------------------
`project`          | `version.project`           | The project name.                 |
`major`            | `version.major`             | The major version number.         | `1`
`minor`            | `version.major`             | The minor version number.         | `0`
`patch`            | `version.patch`             | The patch version number.         | `0`
`preRelease`       | `version.prerelease`        | The pre-release version.          |
`preReleasePrefix` | `version.prerelease.prefix` | The pre-release prefix.           | `-`
`buildMeta`        | `version.buildmeta`         | The build metadata version.       |
`buildMetaPrefix`  | `version.buildmeta.prefix`  | The metadata prefix.              | `+`
`separator`        | `version.separator`         | The version separator.            | `.`
`packageName`      |                             | The package name.                 | _Same as annotated class_
`className`        |                             | The name of the generated class.  | `GeneratedVersion`
`properties`       |                             | The properties file.              |
`template`         |                             | The template file.                | `version.mustache`
`type`             |                             | Either `java` or `kt` for Kotlin. | `java`
`keysPrefix`       |                             | The prefix for all property keys. | `version.`

In order to easily incorporate with existing projects, the property keys may be assigned custom values:

```java
@Version(
  properties = "example.properties",
  keysPrefix = "example."
  majorKey = "maj",
  minorKey = "min",
  patchKey = "build",
  preReleaseKey = "rel",
  buildMetaKey = "meta",
  projectKey = "project"
)
public class Example {
// ...
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

> :warning: `keysPrefix` is a new element staring in `1.1.0` and may break older versions when using custom property keys.  
> :zap: A quick fix is to include `keysPrefix=""` in the annotation to remove the default `version.` prefix.

## Usage with Maven, Gradle, Kotlin and Kobalt

### Maven

To install and run from [Maven](https://maven.apache.org/), configure an artifact as follows:

```xml
<dependency>
    <groupId>net.thauvin.erik</groupId>
    <artifactId>semver</artifactId>
    <version>1.2.0</version>
</dependency>
```

Please look at [pom.xml](https://github.com/ethauvin/semver/blob/master/examples/java/pom.xml) in the [Java example](https://github.com/ethauvin/semver/tree/master/examples/java) directory for a sample:

```bash
mvn verify
```

### Gradle

#### Class Generation

To install and run from [Gradle](https://gradle.org/), add the following to `build.gradle`:

```gradle
dependencies {
    annotationProcessor 'net.thauvin.erik:semver:1.2.0'
    implementation 'net.thauvin.erik:semver:1.2.0'
}
```

The `GeneratedVersion` class will be automatically created in the `build/generated` directory upon compiling.

#### Class & Source Generation

In order to also incorporate the generated source code into the `source tree`, add the following to the very top of `build.gradle`:

```gradle
compileJava.options.annotationProcessorGeneratedSourcesDirectory = file("${projectDir}/src/generated/java")
```

The `GeneratedVersion.java` file will now be located in `src/generated`.

Please look at [build.gradle](https://github.com/ethauvin/semver/blob/master/examples/java/build.gradle) in the [Java example](https://github.com/ethauvin/semver/tree/master/examples/java) directory for a sample.

### Kotlin

The annotation processor also supports [Kotlin](https://kotlinlang.org/).

To generate a Kotlin version file, simply specify the `type` as follows:

```kotlin
import net.thauvin.erik.semver.Version

@Version(properties = "version.properties", type="kt")
open class Main {
// ...
```
The [Kotlin default template](https://github.com/ethauvin/semver/blob/master/src/main/resources/semver-kt.mustache) implements the same static fields and functions as the [Java template](#default-template).

#### Kotlin & Gradle

To install and run from [Gradle](https://gradle.org/), add the following to `build.gradle.kts`:

```kotlin
var semverProcessor = "net.thauvin.erik:semver:1.2.0"

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

The arguments block is not required if `kapt` is configured to use the Gradle Worker API in `gradle.properties`:

```ini
kapt.use.worker.api=true
```

This option will likely be enabled by default in the future, but is currently not working under Java 10+ see [KT-26203](https://youtrack.jetbrains.net/issue/KT-26203). 

Please look at the [Kotlin example](https://github.com/ethauvin/semver/tree/master/examples/kotlin) project for a [build.gradle.kts](https://github.com/ethauvin/semver/blob/master/examples/kotlin/build.gradle.kts) sample.

### Kobalt

To install and run from [Kobalt](https://beust.com/kobalt/), add the following to `Build.kt`:

```gradle
dependencies {
    apt("net.thauvin.erik:semver:1.2.0")
    compileOnly("net.thauvin.erik:semver:1.2.0")
}
```

Please look at [Build.kt](https://github.com/ethauvin/semver/blob/master/examples/java/kobalt/src/Build.kt) in the [Java example](https://github.com/ethauvin/semver/tree/master/examples/java) directory for a sample.


## Auto-Increment

Incrementing the version is best left to your favorite build system. For a solution using Gradle, please have a look at the [__Semver Version Plugin for Gradle__](https://github.com/ethauvin/semver-gradle).

There are also full [examples](https://github.com/ethauvin/semver-gradle/tree/master/examples/annotation-processor) in both [Java](https://github.com/ethauvin/semver-gradle/tree/master/examples/annotation-processor/java) and [Kotlin](https://github.com/ethauvin/semver-gradle/tree/master/examples/annotation-processor/kotlin) showing how to use both the plugin and annotation processor concurrently.
