plugins {
    kotlin("jvm").version("1.3.72")
    application
    id("org.jetbrains.kotlin.kapt").version("1.3.72")
    id("com.github.ben-manes.versions").version("0.28.0")
}

// ./gradlew
// ./gradlew run
// ./gradlew runJava
// ./gradlew runExample
// ./gradlew runJavaExample

defaultTasks(ApplicationPlugin.TASK_RUN_NAME)

val semverProcessor = "net.thauvin.erik:semver:1.2.0"

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
    jcenter()
}

application {
    mainClassName = "com.example.App"
}

tasks {
    register("runJava", JavaExec::class) {
        group = "application"
        main = "com.example.JavaApp"
        classpath = sourceSets["main"].runtimeClasspath

    }

    register("runJavaExample", JavaExec::class) {
        group = "application"
        main = "com.example.JavaExample"
        classpath = sourceSets["main"].runtimeClasspath
    }

    register("runExample", JavaExec::class) {
        group = "application"
        main = "com.example.Example"
        classpath = sourceSets["main"].runtimeClasspath
    }
}
