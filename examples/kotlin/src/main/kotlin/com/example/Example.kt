package com.example

import net.thauvin.erik.semver.Version
import java.text.SimpleDateFormat
import java.util.Locale

@Version(properties = "example.properties", type = "kt", template = "example.mustache", className = "ExampleVersion",
    keysPrefix = "example.")
class Example {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)

            println("-------------------------------------------------------")

            println("  ${ExampleVersion.PROJECT} ${ExampleVersion.VERSION} ("
                + sdf.format(ExampleVersion.BUILDDATE) + ')')

            println("-------------------------------------------------------")
        }
    }
}
