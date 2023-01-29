package ru.otus.homework;

import java.util.ArrayList;
import java.util.List;

public class Statistic {
    private int success = 0;
    private int failed = 0;
    private final List<String> errors;

    public Statistic() {
        this.errors = new ArrayList<>();
    }

    public void addSuccess(int value) {
        this.success += value;
    }

    public void addFailed(int value) {
        this.failed += value;
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public int getSuccess() {
        return this.success;
    }

    public int getFailed() {
        return this.failed;
    }

    public int getTotal() {
        return this.success + this.failed;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return "Total tests: " + getTotal() + ", success: " + getSuccess() + ", failed: " + getFailed();
    }
}
