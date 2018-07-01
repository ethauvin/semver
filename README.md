#  Semantic Version Annotation Processor

[![License (3-Clause BSD)](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg?style=flat-square)](http://opensource.org/licenses/BSD-3-Clause) [![release](https://img.shields.io/github/release/ethauvin/semver.svg)](https://github.com/ethauvin/semver/releases/latest) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.thauvin.erik/semver/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.thauvin.erik/semver) [ ![Download](https://api.bintray.com/packages/ethauvin/maven/SemVer/images/download.svg) ](https://bintray.com/ethauvin/maven/SemVer/_latestVersion)  
[![Known Vulnerabilities](https://snyk.io/test/github/ethauvin/semver/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/ethauvin/semver?targetFile=build.gradle) [![Build Status](https://travis-ci.org/ethauvin/semver.svg?branch=master)](https://travis-ci.org/ethauvin/semver) [![Build status](https://ci.appveyor.com/api/projects/status/nbv4mxd1gpxtx69o?svg=true)](https://ci.appveyor.com/project/ethauvin/semver) [![CircleCI](https://circleci.com/gh/ethauvin/semver/tree/master.svg?style=shield)](https://circleci.com/gh/ethauvin/semver/tree/master)

An [annotation processor](https://docs.oracle.com/javase/8/docs/api/javax/annotation/processing/Processor.html) that automatically generates a `GeneratedVersion` class based on a [Mustache](https://mustache.github.io/) template and containing the [semantic version](http://semver.org/) (major, minor, patch, etc.) that is read from a `Properties` file or defined in the [annotation](https://docs.oracle.com/javase/tutorial/java/annotations/basics.html).

This processor was inspired by Cédric Beust's [version-processor](https://github.com/cbeust/version-processor) and works well in conjunction with the [Semantic Version Plugin for Gralde](https://github.com/ethauvin/semver-gradle).

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

[View Example](https://github.com/ethauvin/semver/tree/master/example)

## Template

Upon running the annotation processor, a source file [`GeneratedVersion.java`](https://github.com/ethauvin/semver/blob/master/example/src/generated/java/net/thauvin/erik/semver/example/GeneratedVersion.java) is automatically generated with static methods to access the semantic version data. The source is based on a fully customizable [Mustache](https://mustache.github.io/) template.

To use your own template, simply create a `version.mustache` file. The processor will automatically look for it.

To specify your own template name, use:

```java
@Version(template = "version.mustache")
public class A {
// ...
```

### Default Template

The [default template](https://github.com/ethauvin/semver/blob/master/src/main/resources/semver.mustache) implements the following static fields:

Field          | Description                      |  Example
:--------------|:---------------------------------|:-----------------
`PROJECT`      | The project name, if any.        | `MyProject`
`BUILDDATE`    | The build date.                  | [`java.util.Date`](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)
`VERSION`      | The full version string.         | `1.2.3-alpha+001`
`MAJOR`        | The major version.               | `1`
`MINOR`        | The minor version.               | `2`
`PATCH`        | The patch version.               | `3`
`PRERELEASE`   | The pre-release version, if any. | `alpha`
`BUILDMETA`    | The build metadata, if any.      | `001`

And the following methods/functions:

Method                   | Description                                               | Example
:------------------------|:----------------------------------------------------------|:--------
`preReleaseWithPrefix()` | Returns the pre-release with a prefix, `-` by default.    | `-alpha`
`buildMetaWithPrefix()`  | Returns the build metadata with a prefix, `+` by default. | `+001`

### Custom Template

A very simple custom template might look something like:

```java
/* version.mustache */
package {{packageName}}

import java.util.Date;

public final class {{className}} {
    public final static String PROJECT = "{{project}}";
    public final static Date DATE = new Date({{epoch}}L);
    public final static int MAJOR = {{major}};
    public final static int MINOR = {{minor}};
    public final static int PATCH = {{patch}};
    public final static String PRERELEASE = "{{preRelease}}";
    public final static String BUILDMETA = "{{buildMeta}}";
}
```
The mustache variables automatically filled in by the processor are:

Variable          | Description                 | Type
:-----------------|:----------------------------|:--------
`{{packageName}}` | The package name.           | `String`
`{{className}}`   | The class name.             | `String`
`{{project}}`     | The project name.           | `String`
`{{epoch}}`       | The build epoch/unix time.  | `long`
`{{major}}`       | The major version.          | `int`
`{{minor}}`       | The minor version.          | `int`
`{{patch}}`       | The patch version.          | `int`
`{{preRelease}}`  | The pre/release version.    | `String`
`{{buildMeta}}`   | The build metadata version. | `String`

Please also look at this [example](https://github.com/ethauvin/mobibot/blob/master/version.mustache) using [`java.time`](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html)

## Elements & Properties

The following annotation elements and properties are available:

Element       | Property             | Description                       | Default
:-------------|:---------------------|:----------------------------------|:-------------------------
`project`     | `version.project`    | The project name.                 |
`major`       | `version.major`      | The major version number.         | `1`
`minor`       | `version.major`      | The minor version number.         | `0`
`patch`       | `version.patch`      | The patch version number.         | `0`
`preRelease`  | `version.prerelease` | The pre-release version.          |
`buildMeta`   | `version.buildmeta`  | The build metadata version.       |
`packageName` |                      | The package name.                 | _Same as annotated class_
`className`   |                      | The name of the generated class.  | `GeneratedVersion`
`properties`  |                      | The properties file.              |
`template`    |                      | The template file.                | `version.mustache`
`type`        |                      | Either `java` or `kt` for Kotlin. | `java`
`keysPrefix`  |                      | The prefix for all property keys. | `version.`

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

> :warning: `keysPrefix` is a new element in `1.1.0` and may break older versions when using custom property keys.  
> :zap: A quick fix is to include `keysPrefix=""` in the annotation to remove the default `version.` prefix.

## Usage with Maven, Grail, Kobalt and Kotlin

### Maven

To install and run from [Maven](http://maven.apache.org/), configure an artifact as follows:

```xml
<dependency>
    <groupId>net.thauvin.erik</groupId>
    <artifactId>semver</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Gradle

#### Class Generation

To install and run from [Gradle](https://gradle.org/), add the following to the `build.gradle` file:

```gradle
dependencies {
    annotationProcessor 'net.thauvin.erik:semver:1.1.0'
    compileOnly 'net.thauvin.erik:semver:1.1.0'
}
```

The `GeneratedVersion` class will be automatically created in the `build/generated` directory upon compiling.

#### Class & Source Generation

In order to also incorporate the generated source code into the `source tree`, add the following to the very top of the `build.gradle` file:

```gradle
compileJava.options.annotationProcessorGeneratedSourcesDirectory = file("${projectDir}/src/generated")
```

The `GeneratedVersion.java` file will now be located in `src/generated`.

Please look at the [build.gradle](https://github.com/ethauvin/semver/blob/master/example/build.gradle) file in the [example](https://github.com/ethauvin/semver/tree/master/example) module directory for a sample.

### Kobalt

To install and run from [Kobalt](http://beust.com/kobalt/), add the following to the `Build.kt` file:

```gradle
dependencies {
    apt("net.thauvin.erik:semver:1.1.0")
    compileOnly("net.thauvin.erik:semver:1.1.0")
}
```

Please look at the [Build.kt](https://github.com/ethauvin/semver/blob/master/example/kobalt/src/Build.kt) file in the [example](https://github.com/ethauvin/semver/tree/master/example) module directory for a sample.

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

Please look at the [Example for Kotlin](https://github.com/ethauvin/semver-example-kotlin) project for samples on using Gradle ([build.gradle](https://github.com/ethauvin/semver-example-kotlin/blob/master/build.gradle)) and Kobalt ([Build.kt](https://github.com/ethauvin/semver-example-kotlin/blob/master/kobalt/src/Build.kt)).

## Auto-Increment

Incrementing the version is best left to your favorite build system. For a solution using Gradle, please have a look at the [Semver Version Plugin for Gradle](https://github.com/ethauvin/semver-gradle).