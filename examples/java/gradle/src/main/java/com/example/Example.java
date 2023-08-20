package com.example;

import net.thauvin.erik.semver.Version;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Version(properties = "example.properties", template = "example.mustache", className = "ExampleVersion",
        keysPrefix = "example.")
public final class Example {
    /**
     * Command line interface.
     *
     * @param args The command line parameters.
     */
    public static void main(final String... args) {
        final var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

        System.out.println("-- From Example -------------------------------------");

        System.out.println("  " + ExampleVersion.PROJECT + ' ' + ExampleVersion.VERSION
                + " (" + sdf.format(ExampleVersion.BUILDDATE) + ')');

        System.out.println("-----------------------------------------------------");
    }
}

