package ru.otus.homework;


import ru.otus.homework.annotations.Log;

public interface TestLoggingInterface {
    void calculation(int param1);

    void calculation(int param1, int param2);

    void calculation(int param1, int param2, int param3);
}
