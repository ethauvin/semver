import com.beust.kobalt.*
import com.beust.kobalt.plugin.application.application
import com.beust.kobalt.plugin.apt.apt
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.project
import java.io.FileInputStream
import java.util.*


val bs = buildScript {
    repos(file("K:/maven/repository/"))
}

fun StringBuilder.prepend(s: String): StringBuilder {
    if (this.isNotEmpty()) {
        this.insert(0, s)
    }
    return this
}

val example = project {

    name = "example"

    fun versionFor(): String {
        val propsFile = "version.properties"
        val majorKey = "version.major"
        val minorKey = "version.minor"
        val patchKey = "version.patch"
        val metaKey = "version.buildmeta"
        val preKey = "version.prerelease"

        val p = Properties().apply { FileInputStream(propsFile).use { fis -> load(fis) } }

        return (p.getProperty(majorKey, "1") + "." + p.getProperty(minorKey, "0") + "." + p.getProperty(patchKey, "0")
                + StringBuilder(p.getProperty(preKey, "")).prepend("-")
                + StringBuilder(p.getProperty(metaKey, "")).prepend("+"))
    }

    version = versionFor()

    val mainClassName = "net.thauvin.erik.semver.example.Example"
    val processorJar = "net.thauvin.erik:semver:0.9.7"

    dependencies {
        apt(processorJar)
        compileOnly(processorJar)
    }

    apt {
        outputDir = "../src/generated/java/"
    }

    install {
        target = "deploy"
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
}