import com.beust.kobalt.localMaven
import com.beust.kobalt.plugin.application.application
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.plugin.packaging.install
import com.beust.kobalt.project
import com.beust.kobalt.repos
import java.io.FileInputStream
import java.util.*

val repos = repos(localMaven())

fun StringBuilder.prepend(s: String): StringBuilder {
    if (this.length > 0) {
        this.insert(0, s)
    }
    return this
}

val semver = project {

    name = "semver"
    group = "net.thauvin.erik"
    artifactId = name

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
        compile("org.testng:testng:6.9.12")

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

}
