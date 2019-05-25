import com.beust.kobalt.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.apt.*
import com.beust.kobalt.plugin.java.javaCompiler
import com.beust.kobalt.plugin.packaging.*

// ./kobaltw run

val bs = buildScript {
    repos(localMaven())
}

val example = project {

    name = "example"
    version = "1.0"

    val mainClassName = "com.example.App"
    val processorJar = "net.thauvin.erik:semver:1.2.0"

    dependencies {
        apt(processorJar)
        compileOnly(processorJar)
    }

    apt {
        //outputDir = "../src/generated/java/"
    }

    javaCompiler {
        args("-source", "1.8", "-target", "1.8")
    }

    assemble {
        jar {
            manifest {
                attributes("Main-Class", mainClassName)
            }
        }
    }

    application {
        mainClass = mainClassName
    }

    application {
        taskName = "runExample"
        mainClass = "com.example.Example"
    }
}
