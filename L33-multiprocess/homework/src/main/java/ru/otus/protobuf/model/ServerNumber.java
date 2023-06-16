package ru.otus.protobuf.model;

public class ServerNumber {
    private int value;
    private boolean isUsed;

    public void setValue(int value) {
        this.value = value;
        isUsed = false;
    }

    public int getValue() {
        isUsed = true;
        return value;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
