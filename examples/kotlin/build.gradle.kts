plugins {
    kotlin("jvm") version "1.3.0"
    application
    id("org.jetbrains.kotlin.kapt") version "1.3.0"
}

// ./gradlew
// ./gradlew runJava
// ./gradlew run runJava

defaultTasks(ApplicationPlugin.TASK_RUN_NAME)

var semverProcessor = "net.thauvin.erik:semver:1.1.0-beta"

dependencies {
    kapt(semverProcessor)
    compileOnly(semverProcessor)

    compile(kotlin("stdlib"))
}

repositories {
    mavenLocal()
    jcenter()
}

application {
    mainClassName = "com.example.Main"
}

tasks {
    register("runJava", JavaExec::class) {
        main = "com.example.Example"
        classpath = sourceSets["main"].runtimeClasspath
    }
}
