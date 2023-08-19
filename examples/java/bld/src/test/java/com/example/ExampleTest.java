package com.example;

public class ExampleTest {
    private static final String HELLO = "Hello!";

    public static void main(String[] args) {
        var hello = new ExampleTest().verifyHello();
        if (HELLO.equals(hello)) {
            System.out.println("Succeeded");
        } else {
            throw new AssertionError();
        }
    }

    String verifyHello() {
        return HELLO;
    }
}
