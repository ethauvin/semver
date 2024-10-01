package com.example;

import rife.bld.BuildCommand;
import rife.bld.Project;

import java.io.File;
import java.util.List;

import static rife.bld.dependencies.Repository.MAVEN_CENTRAL;
import static rife.bld.dependencies.Repository.MAVEN_LOCAL;
import static rife.bld.dependencies.Scope.provided;

/**
 * Example build.
 */
public class ExampleBuild extends Project {
    public ExampleBuild() {
        pkg = "com.example";
        name = "Example";
        version = version(0, 1, 0);

        mainClass = "com.example.App";

        testOperation().mainClass("com.example.ExampleTest");

        repositories = List.of(MAVEN_LOCAL, MAVEN_CENTRAL);

        scope(provided).include(dependency("net.thauvin.erik", "semver", version(1, 2, 2, "SNAPSHOT")));
    }

    public static void main(String[] args) {
        new ExampleBuild().start(args);
    }

    /**
     * Saves generated source files in the {@code build/generated} directory.
     * <p>
     * To incorporate the generated source code into the source tree, add this directory as an additional source
     * location in your IDE.
     */
    @Override
    public void compile() throws Exception {
        var generated = new File(buildDirectory(), "generated");
        var ignore = generated.mkdir();
        compileOperation().compileOptions().addAll(List.of("-proc:full", "-s", generated.getAbsolutePath()));
        super.compile();
    }

    @BuildCommand(value = "run-example", summary = "Runs the example")
    public void runExample() throws Exception {
        runOperation().fromProject(this).mainClass("com.example.Example").execute();
    }
}
