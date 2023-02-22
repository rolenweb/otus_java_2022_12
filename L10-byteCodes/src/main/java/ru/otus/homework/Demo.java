package ru.otus.homework;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = new ProxyTestLoggingFactory().create();

        testLogging.calculation(6);
        testLogging.calculation(3, 5);
        testLogging.calculation(1, 2, 3);
    }
}
