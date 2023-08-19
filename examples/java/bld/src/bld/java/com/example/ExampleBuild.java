package com.example;

import rife.bld.BuildCommand;
import rife.bld.Project;

import java.util.List;

import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.compile;

public class ExampleBuild extends Project {
    public ExampleBuild() {
        pkg = "com.example";
        name = "Example";
        version = version(0, 1, 0);

        mainClass = "com.example.App";

        testOperation().mainClass("com.example.ExampleTest");

        repositories = List.of(MAVEN_LOCAL, MAVEN_CENTRAL);

        scope(compile)
                .include(dependency("net.thauvin.erik", "semver", version(1, 2, 1, "SNAPSHOT")));
    }

    @BuildCommand(summary = "Run the example")
    public void runExample() throws Exception {
        runOperation().executeOnce(() -> runOperation().fromProject(this).mainClass("com.example.Example"));
    }

    public static void main(String[] args) {
        new ExampleBuild().start(args);
    }
}
