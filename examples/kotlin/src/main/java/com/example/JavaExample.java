package com.example;

import java.text.SimpleDateFormat;
import java.util.Locale;

public final class JavaExample {
    /**
     * Command line interface.
     *
     * @param args The command line parameters.
     */
    public static void main(final String... args) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

        System.out.println("-- From JavaExample -----------------------------------");

        System.out.println("  " + ExampleVersion.PROJECT + ' ' + ExampleVersion.VERSION
            + " (" + sdf.format(ExampleVersion.BUILDDATE) + ')');

        System.out.println("-------------------------------------------------------");
    }
}
