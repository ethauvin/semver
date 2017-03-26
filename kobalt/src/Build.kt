import com.beust.kobalt.file
import com.beust.kobalt.localMaven
import com.beust.kobalt.plugin.application.application
import com.beust.kobalt.plugin.apt.apt
import com.beust.kobalt.plugin.java.javaCompiler
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.plugin.packaging.install
import com.beust.kobalt.plugins
import com.beust.kobalt.project
import com.beust.kobalt.repos
import java.io.FileInputStream
import java.util.*
import net.thauvin.erik.kobalt.plugin.exec.*

val bs = buildScript {
    plugins("net.thauvin.erik:kobalt-exec:")
}

fun StringBuilder.prepend(s: String): StringBuilder {
    if (this.length > 0) {
        this.insert(0, s)
    }
    return this
}

fun versionFor(directory: String = "./"): String {
    val propsFile = directory + '/' + "version.properties"
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

val semver = project {

    name = "semver"
    group = "net.thauvin.erik"
    artifactId = name
    version = versionFor()

    dependencies {
        compile("org.apache.velocity:velocity:1.7")
    }

    dependenciesTest {
        compile("org.testng:testng:6.11")
    }

    install {
        libDir = "deploy"
    }

    assemble {
        jar {}
    }

    application {
        mainClass = "com.example.Main"
    }

    exec {
        val args = listOf("--from", "markdown_github", "--to", "html5", "-s", "-c", "github-pandoc.css", "-o", "README.html", "README.md")
        commandLine(listOf("pandoc") + args, os = setOf(Os.LINUX))
        commandLine(listOf("cmd", "/c", "pandoc") + args, os = setOf(Os.WINDOWS))
    }
}

val example = project(semver){

    name = "example"
    directory = "example"
    version = versionFor(directory)

    val mainClassName = "net.thauvin.erik.semver.example.Example"

    sourceDirectories {
        path("src/main/java")
    }

    sourceDirectoriesTest {
        path("src/test/java")
    }

    dependencies {
        compile("org.apache.velocity:velocity:1.7")
    }

    dependenciesTest {

    }

    install {
        libDir = "example/deploy"
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
