plugins {
    id("application")
    id("com.github.ben-manes.versions") version "0.51.0"
    kotlin("jvm") version "1.9.23"
    kotlin("kapt") version "1.9.23"
}

// ./gradlew
// ./gradlew run
// ./gradlew runJava
// ./gradlew runExample
// ./gradlew runJavaExample

defaultTasks(ApplicationPlugin.TASK_RUN_NAME)

val semverProcessor = "net.thauvin.erik:semver:1.2.2-SNAPSHOT"

dependencies {
    kapt(semverProcessor)
    implementation(semverProcessor)

    implementation(kotlin("stdlib"))
}

kapt {
    arguments {
        arg("semver.project.dir", projectDir)
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

application {
    mainClass.set("com.example.App")
}

tasks {
    register("runJava", JavaExec::class) {
        group = "application"
        mainClass.set("com.example.JavaApp")
        classpath = sourceSets.main.get().runtimeClasspath

    }

    register("runJavaExample", JavaExec::class) {
        group = "application"
        mainClass.set("com.example.JavaExample")
        classpath = sourceSets.main.get().runtimeClasspath
    }

    register("runExample", JavaExec::class) {
        group = "application"
        mainClass.set("com.example.Example")
        classpath = sourceSets.main.get().runtimeClasspath
    }
}
