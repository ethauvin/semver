package com.example

import net.thauvin.erik.semver.Version
import java.text.SimpleDateFormat
import java.util.Locale

@Version(properties = "version.properties", type = "kt")
class App {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val sdf = SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z", Locale.US)

            println("-----------------------------------------------------")

            println("  Version: ${GeneratedVersion.PROJECT} ${GeneratedVersion.VERSION}")

            println("    Built on:       " + sdf.format(GeneratedVersion.BUILDDATE))
            println("    Major:          ${GeneratedVersion.MAJOR}")
            println("    Minor:          ${GeneratedVersion.MINOR}")
            println("    Patch:          ${GeneratedVersion.PATCH}")
            println("    PreRelease:     ${GeneratedVersion.PRERELEASE}")
            println("    BuildMetaData:  ${GeneratedVersion.BUILDMETA}")

            println("-----------------------------------------------------")
        }
    }
}
