plugins {
    kotlin("jvm") version "1.2.50"
    application
    id("org.jetbrains.kotlin.kapt") version "1.2.50"
}

// ./gradlew
// ./gradlew runJava
// ./gradlew run runJava

defaultTasks(ApplicationPlugin.TASK_RUN_NAME)

var semverProcessor = "net.thauvin.erik:semver:1.0.1"

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
    val runJava by creating(JavaExec::class) {
        main = "com.example.Example"
        classpath = java.sourceSets["main"].runtimeClasspath
    }
}