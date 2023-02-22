package ru.otus.homework;

import ru.otus.homework.annotations.Log;

public class TestLoggingImpl implements TestLoggingInterface{
    @Log
    @Override
    public void calculation(int param1) {
        System.out.println("calculation with 1 param");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("calculation with 2 params");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, int param3) {
        System.out.println("calculation with 3 params");
    }
}
