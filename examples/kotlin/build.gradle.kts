plugins {
    kotlin("jvm").version("1.3.21")
    application
    id("org.jetbrains.kotlin.kapt").version("1.3.21")
}

// ./gradlew
// ./gradlew runJava
// ./gradlew run runJava

defaultTasks(ApplicationPlugin.TASK_RUN_NAME)

var semverProcessor = "net.thauvin.erik:semver:1.2.0"

dependencies {
    kapt(semverProcessor)
    compileOnly(semverProcessor)

    implementation(kotlin("stdlib"))
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
