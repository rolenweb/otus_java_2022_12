package ru.otus.l12.homework;

import java.util.List;
import java.util.Stack;

public class BanknoteFactory {

    public static Banknote create(int nominal) {
        return new Banknote(nominal);
    }
    public static Stack<Banknote> createStack(int count, int nominal) {
        Stack<Banknote> banknotes = new Stack<>();
        while (count > 0) {
            banknotes.push(create(nominal));
            count--;
        }

        return banknotes;
    }
}
