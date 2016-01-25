#  Semantic Version Annotation Processor

An [annotation processor](https://docs.oracle.com/javase/8/docs/api/javax/annotation/processing/Processor.html) that automatically generates a `Version` class containing the [semantic version](http://semver.org/) (major, minor, patch, etc.) that is read from a `Properties` file or defined in the [annotation](https://docs.oracle.com/javase/tutorial/java/annotations/basics.html).

This processor was inspired by Cédric Beust's [version-processor](https://github.com/cbeust/version-processor).

## Examples
 
* Using annotation elements:

```java
@Version(major = 1, minor = 0, patch = 0, prerelease = "beta")
public class A {
// ...
```

* Or using a [properties](https://docs.oracle.com/javase/tutorial/essential/environment/properties.html) file:

```java
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

Upon running the annotator processor, a source file `GeneratedVersion.java` is automatically generated with static methods to access the semantic version data. The source is based on a fully customizable [Velocity](http://velocity.apache.org/) template.

```java
@Version(template = "myversion.vm")
public class A {
// ...
```

### Default Template

The default template implements the following static methods:

Method            | Description                      |  Example
------------------|----------------------------------|------------------
`getProject`      | The project name, if any.        | `MyProject`
`getBuildDate`    | The build date.                  | [`java.util.Date`](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)
`getVersion`      | The full version string.         | `1.0.0-alpha+001`
`getMajor`        | The major version.               | `1`
`getMinor`        | The minor version.               | `0`
`getPatch`        | The patch version.               | `0`
`getPreRelease`   | The pre-release version, if any. | `alpha`
`getBuildMetadata`| The build metadata, if any.      | `001`

### Custom Template

A very simple custom template might look something like:

```java
/* myversion.vm */
package ${packageName}

import java.util.Date;

public final class ${className} {
	public final static String BUILDMETA = "${buildmeta}";
	public final static Date DATE = new Date(${epoch}L);
	public final static int MAJOR = ${major};
	public final static int MINOR = ${minor};
	public final static int PATCH = ${patch};
	public final static String PRERELEASE = "${prerelease}";
	public final static String PROJECT = "${project}";
}
```
The Velocity variables are automatically filled in by the processor.
	
## Elements & Properties

The following annotation elements and properties are available:

Element      | Property             | Description                      | Default
-------------|----------------------|----------------------------------|-------------
`project`    | `version.project`    | The project name.                |
`major`      | `version.major`      | The major version number.        | `1`
`minor`      | `version.major`      | The minor version number.        | `0`
`patch`      | `version.patch`      | The patch version number.        | `0`
`prerelease` | `version.prerelease` | The pre-release version.         |
`buildmeta`  | `version.buildmeta`  | The build metadata version.      |
`className`  |                      | The name of the generated class. | `GeneratedVersion`
`properties` |                      | The properties file.             |
`template`   |                      | The template file.               | `version.vm`

In order to easily incorporate with existing projects, the property keys may be assigned custom values:

```java
@Version(
  properties = "example.properties", 
  majorKey = "example.major",
  minorKey = "example.minor",
  patchKey = "example.patch",
  prereleaseKey = "example.prerelease",
  buildmetaKey = "example.buildmeta",
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
## Usage with Maven, Grail and Kobalt

### Maven

To install and run from [Maven](http://maven.apache.org/), configure an artifact as follows:

```xml
<dependency>
    <groupId>net.thauvin.erik</groupId>
    <artifactId>semver</artifactId>
    <version>0.9.1-beta</version>
</dependency>
```

### Gradle

To install and run from [Gradle](https://gradle.org/), add the following to the `build.gradle` file:

```gradle
dependencies {
    compile 'net.thauvin.erik:semver:0.9.1-beta'
}
```

The `GeneratedVersion` class will be automatically generated in the `build` directory upon compiling.

In order to also include the generated source code to your source tree, you should use the [EWEK Annotation Processor Plugin](https://github.com/ewerk/gradle-plugins/tree/master/plugins/annotation-processor-plugin). Start by addding the following to the very top of the `build.gradle` file:

```gradle
plugins {
    id "com.ewerk.gradle.plugins.annotation-processor" version "1.0.2"
}
```

Then add the following to the `build.gradle` file:

```gradle
dependencies {
    compile 'net.thauvin.erik:semver:0.9.1-beta'
}

annotationProcessor {
    project.version = getVersion(isRelease)
    library 'net.thauvin.erik:semver:0.9.1-beta'
    processor 'net.thauvin.erik.semver.VersionProcessor'
    // sourcesDir 'src/generated/java'
}

compileJava {
    // Disable the classpath procesor
    options.compilerArgs << '-proc:none'
}
```

The plugin implements a separate compile task that only runs the annotation processor and is executed during the build phase.

Please look at the `build.gradle` file in the `example` module directory for a sample.

### Kobalt

To install and run from [Kobalt](http://beust.com/kobalt/), add the following to the `Build.kt` file:

```gradle
dependencies {
    apt("net.thauvin.erik:semver:0.9.1-beta")
}
```

### Auto-Increment

Incrementing the version is best left to your favorite build system.

For a solution using [Gradle](https://gradle.org/), please have a look at the `build.gradle` file in the `example` module directory. To run the example with patch version auto-incrementing, issue the following command:

```
gradle clean release run
```
