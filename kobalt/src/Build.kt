import com.beust.kobalt.*
import com.beust.kobalt.plugin.application.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.publish.*
import net.thauvin.erik.kobalt.plugin.exec.*
import org.apache.maven.model.*
import java.io.*
import java.util.*

val bs = buildScript {
    plugins("net.thauvin.erik:kobalt-maven-local:")
    plugins("net.thauvin.erik:kobalt-exec:")
    repos(localMaven())
}

fun StringBuilder.prepend(s: String): StringBuilder {
    if (this.isNotEmpty()) {
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

    pom = Model().apply {
        description = "Semantic Version Annotation Processor"
        url = "https://github.com/ethauvin/semver"
        licenses = listOf(License().apply {
            name = "BSD 3-Clause"
            url = "https://opensource.org/licenses/BSD-3-Clause"
        })
        scm = Scm().apply {
            url = "https://github.com/ethauvin/semver"
            connection = "https://github.com/ethauvin/semver.git"
            developerConnection = "git@github.com:ethauvin/semver.git"
        }
        developers = listOf(Developer().apply {
            id = "ethauvin"
            name = "Erik C. Thauvin"
            email = "erik@thauvin.net"
        })
    }

    dependencies {
        compile("com.github.spullara.mustache.java:compiler:0.9.4")
    }

    dependenciesTest {
        compile("org.testng:testng:6.11")
    }

    install {
        target = "deploy"
    }

    assemble {
        jar {
            fatJar = true
        }
        mavenJars {}
    }

    autoGitTag {
        enabled = true
        message = "Version $version"
    }

    bintray {
        publish = true
        description = "Release version $version"
        vcsTag = version
        sign = true
    }


    exec {
        val args = listOf("--from", "markdown_github", "--to", "html5", "-s", "-c", "github-pandoc.css", "-o", "README.html", "README.md")
        commandLine(listOf("pandoc") + args, os = setOf(Os.LINUX))
        commandLine(listOf("cmd", "/c", "pandoc") + args, os = setOf(Os.WINDOWS))
    }
}