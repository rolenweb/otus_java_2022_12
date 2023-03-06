package ru.otus.l12.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Cell {
    private final int nominal;
    private final Stack<Banknote> banknotes;

    public Cell(int nominal, Stack<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            if (banknote.getValue() != nominal) {
                throw new RuntimeException("Nominal value must be " + nominal);
            }
        }
        this.nominal = nominal;
        this.banknotes = banknotes;
    }

    public int getValue() {
        return banknotes.size() * nominal;
    }

    public void add(Banknote banknote) {
        if (banknote.getValue() != nominal) {
            throw new RuntimeException("Nominal value must be " + nominal);
        }
        banknotes.push(banknote);
    }

    public List<Banknote> withdraw(int count) {
        if (count > banknotes.size()) {
            throw new RuntimeException("Not enough banknotes with a nominal " + nominal);
        }
        List<Banknote> withdrawingBanknotes = new ArrayList<>();
        while (count > 0) {
            withdrawingBanknotes.add(banknotes.pop());
            count--;
        }

        return withdrawingBanknotes;
    }

}
