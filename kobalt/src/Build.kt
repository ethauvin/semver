import com.beust.kobalt.buildScript
import com.beust.kobalt.localMaven
import com.beust.kobalt.plugin.java.javadoc
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.plugin.packaging.install
import com.beust.kobalt.plugin.publish.autoGitTag
import com.beust.kobalt.plugin.publish.bintray
import com.beust.kobalt.project
import net.thauvin.erik.kobalt.plugin.exec.Os
import net.thauvin.erik.kobalt.plugin.exec.exec
import net.thauvin.erik.kobalt.plugin.versioneye.versionEye
import org.apache.maven.model.Developer
import org.apache.maven.model.License
import org.apache.maven.model.Model
import org.apache.maven.model.Scm
import java.io.FileInputStream
import java.util.*

val bs = buildScript {
    repos(localMaven())
    plugins("net.thauvin.erik:kobalt-exec:",
            "net.thauvin.erik:kobalt-versioneye:")
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
    description = "Semantic Version Annotation Processor"
    group = "net.thauvin.erik"
    artifactId = name
    version = versionFor()

    pom = Model().apply {
        description = project.description
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

    assemble {
        jar {
            //fatJar = true
        }
        mavenJars {
            //fatJar = true
        }
    }

    install {
        target = "deploy"
    }

    autoGitTag {
        enabled = true
        push = false
        message = "Version $version"
    }

    bintray {
        name = "SemVer"
        publish = true
        description = "Release version $version"
        issueTrackerUrl = "https://github.com/ethauvin/semver/issues"
        vcsTag = version
        sign = true
    }

    javadoc {
        title = "${project.description} ${project.version}"
        tags("created")
        author = true
        //quiet = false
        //verbose = true
        links("http://docs.oracle.com/javase/8/docs/api/")
    }

    exec {
        taskName = "pandoc"
        val args = arrayOf("--from", "markdown_github",
                "--to", "html5",
                "-s",
                "-c", "github-pandoc.css",
                "-o", "docs/README.html",
                "README.md")
        commandLine("pandoc", *args, os = setOf(Os.LINUX, Os.MINGW, Os.CYGWIN))
        commandLine("cmd", "/c", "pandoc", *args, os = setOf(Os.WINDOWS))
    }

    versionEye {
        org = "thauvin"
        team = "Owners"
    }
}