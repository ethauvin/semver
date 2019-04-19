plugins {
    kotlin("jvm").version("1.3.30")
    application
    id("org.jetbrains.kotlin.kapt").version("1.3.30")
    id("com.github.ben-manes.versions").version("0.21.0")
}

// ./gradlew
// ./gradlew runJava
// ./gradlew run runJava

defaultTasks(ApplicationPlugin.TASK_RUN_NAME)

var semverProcessor = "net.thauvin.erik:semver:1.1.1"

dependencies {
    kapt(semverProcessor)
    compileOnly(semverProcessor)

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
    mainClassName = "com.example.Main"
}

tasks {
    register("runJava", JavaExec::class) {
        main = "com.example.Example"
        classpath = sourceSets["main"].runtimeClasspath
    }
}
