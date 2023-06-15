package ru.otus.protobuf.service;

public class GeneratorServiceImpl implements GeneratorService {
    private int value = 0;

    @Override
    public int generate() {
        return value++;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
