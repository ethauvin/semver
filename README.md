#  Semantic Version Annotation Processor

[![License (3-Clause BSD)](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg?style=flat-square)](http://opensource.org/licenses/BSD-3-Clause) [![Dependency Status](https://www.versioneye.com/user/projects/56a680101b78fd00390001d2/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56a680101b78fd00390001d2) [![Build Status](https://travis-ci.org/ethauvin/semver.svg?branch=master)](https://travis-ci.org/ethauvin/semver) [![Build status](https://ci.appveyor.com/api/projects/status/nbv4mxd1gpxtx69o?svg=true)](https://ci.appveyor.com/project/ethauvin/semver) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.thauvin.erik/semver/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.thauvin.erik/semver)  [ ![Download](https://api.bintray.com/packages/ethauvin/maven/SemVer/images/download.svg) ](https://bintray.com/ethauvin/maven/SemVer/_latestVersion)

An [annotation processor](https://docs.oracle.com/javase/8/docs/api/javax/annotation/processing/Processor.html) that automatically generates a `GeneratedVersion` class based on a [Mustache](https://mustache.github.io/) template and containing the [semantic version](http://semver.org/) (major, minor, patch, etc.) that is read from a `Properties` file or defined in the [annotation](https://docs.oracle.com/javase/tutorial/java/annotations/basics.html).

This processor was inspired by Cédric Beust's [version-processor](https://github.com/cbeust/version-processor).

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

## Template

Upon running the annotation processor, a source file [`GeneratedVersion.java`](https://github.com/ethauvin/semver/blob/master/example/src/generated/java/net/thauvin/erik/semver/example/GeneratedVersion.java) is automatically generated with static methods to access the semantic version data. The source is based on a fully customizable [Mustache](https://mustache.github.io/) template.

To use your own template, simply create a `version.mustache` file. The processor will automatically look for it.

To specify your own template name, use:

```java
@Version(template = "myversion.mustache")
public class A {
// ...
```

### Default Template

The [default template](https://github.com/ethauvin/semver/blob/master/src/main/resources/semver.mustache) implements the following static fields:

Field          | Description                      |  Example
:--------------|:---------------------------------|:-----------------
`PROJECT`      | The project name, if any.        | `MyProject`
`BUILDDATE`    | The build date.                  | [`java.util.Date`](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)
`VERSION`      | The full version string.         | `1.0.0-alpha+001`
`MAJOR`        | The major version.               | `1`
`MINOR`        | The minor version.               | `0`
`PATCH`        | The patch version.               | `0`
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

Element      | Property             | Description                       | Default
:------------|:---------------------|:----------------------------------|:----------------
`project`    | `version.project`    | The project name.                 |
`major`      | `version.major`      | The major version number.         | `1`
`minor`      | `version.major`      | The minor version number.         | `0`
`patch`      | `version.patch`      | The patch version number.         | `0`
`preRelease` | `version.prerelease` | The pre-release version.          |
`buildMeta`  | `version.buildmeta`  | The build metadata version.       |
`className`  |                      | The name of the generated class.  | `GeneratedVersion`
`properties` |                      | The properties file.              |
`template`   |                      | The template file.                | `version.mustache`
`type`       |                      | Either `java` or `kt` for Kotlin. | `java`

In order to easily incorporate with existing projects, the property keys may be assigned custom values:

```java
@Version(
  properties = "example.properties",
  majorKey = "example.major",
  minorKey = "example.minor",
  patchKey = "example.patch",
  preReleaseKey = "example.prerelease",
  buildMetaKey = "example.buildmeta",
  projectKey = "example.project"
)
public class Example {
// ...
```

```ini
# example.properties
example.project=Example
example.major=1
example.minor=0
example.patch=0
# ...
```
## Usage with Maven, Grail, Kobalt and Kotlin

### Maven

To install and run from [Maven](http://maven.apache.org/), configure an artifact as follows:

```xml
<dependency>
    <groupId>net.thauvin.erik</groupId>
    <artifactId>semver</artifactId>
    <version>1.0</version>
</dependency>
```

### Gradle

#### Class Generation

To install and run from [Gradle](https://gradle.org/), add the following to the `build.gradle` file:

```gradle
dependencies {
    compileOnly 'net.thauvin.erik:semver:1.0'
}
```

The `GeneratedVersion` class will be automatically created in the `build/generated` directory upon compiling.

#### Class & Source Generation

In order to also incorporate the generated source code into the `source tree`, use the [EWERK Annotation Processor Plugin](https://github.com/ewerk/gradle-plugins/tree/master/annotation-processor-plugin). Start by adding the following to the very top of the `build.gradle` file:

```gradle
plugins {
    id "com.ewerk.gradle.plugins.annotation-processor" version "1.0.4"
}
```

Then add the following to the `build.gradle` file:

```gradle
dependencies {
    compileOnly 'net.thauvin.erik:semver:1.0'
}

annotationProcessor {
    library 'net.thauvin.erik:semver:1.0'
    processor 'net.thauvin.erik.semver.VersionProcessor'
    // sourcesDir 'src/generated/java'
}

compileJava {
    // Disable the classpath processor
    options.compilerArgs << '-proc:none'
}
```

The plugin implements a separate compile task that only runs the annotation processor and is executed during the build phase.

Please look at the [build.gradle](https://github.com/ethauvin/semver/blob/master/example/build.gradle) file in the [example](https://github.com/ethauvin/semver/tree/master/example) module directory for a sample.

### Kobalt

To install and run from [Kobalt](http://beust.com/kobalt/), add the following to the `Build.kt` file:

```gradle
dependencies {
    apt("net.thauvin.erik:semver:1.0")
    compileOnly("net.thauvin.erik:semver:1.0")
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

Please look at the [Example for Kotlin](https://github.com/ethauvin/semver-example-kotlin) project for samples on using [Gradle](https://gradle.org/) and [Kobalt](http://beust.com/kobalt/).

### Auto-Increment

Incrementing the version is best left to your favorite build system.

For a solution using [Gradle](https://gradle.org/), please have a look at the [build.gradle](https://github.com/ethauvin/semver/blob/master/example/build.gradle) file in the [example](https://github.com/ethauvin/semver/tree/master/example) module directory. To run the example with patch version auto-incrementing, issue the following command:

```
gradle release run
```

For a solution using [Kobalt](http://beust.com/kobalt/) look at my [Property File Editor](https://github.com/ethauvin/kobalt-property-file) plug-in.