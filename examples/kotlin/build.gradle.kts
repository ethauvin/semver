plugins {
    kotlin("jvm").version("1.5.0")
    application
    id("org.jetbrains.kotlin.kapt").version("1.5.0")
    id("com.github.ben-manes.versions").version("0.38.0")
}

// ./gradlew
// ./gradlew run
// ./gradlew runJava
// ./gradlew runExample
// ./gradlew runJavaExample

defaultTasks(ApplicationPlugin.TASK_RUN_NAME)

val semverProcessor = "net.thauvin.erik:semver:1.2.1-SNAPSHOT"

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
}

application {
    mainClass.set("com.example.App")
}

tasks {
    register("runJava", JavaExec::class) {
        group = "application"
        main = "com.example.JavaApp"
        classpath = sourceSets.main.get().runtimeClasspath

    }

    register("runJavaExample", JavaExec::class) {
        group = "application"
        main = "com.example.JavaExample"
        classpath = sourceSets.main.get().runtimeClasspath
    }

    register("runExample", JavaExec::class) {
        group = "application"
        main = "com.example.Example"
        classpath = sourceSets.main.get().runtimeClasspath
    }
}
