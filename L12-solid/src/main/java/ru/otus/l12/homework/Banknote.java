package ru.otus.l12.homework;

public class Banknote {
    private final int value;

    public Banknote(int value) {
        if (value <= 0) {
            throw new RuntimeException("Banknote value must be greater than 0");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
