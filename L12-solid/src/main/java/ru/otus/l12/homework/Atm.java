package ru.otus.l12.homework;

import java.util.*;

public class Atm {
    private final TreeMap<Integer, Cell> cells;

    public Atm(Map<Integer, Cell> cells) {
        this.cells = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        this.cells.putAll(cells);
    }

    public void deposit(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            if (!cells.containsKey(banknote.getValue())) {
                throw new RuntimeException("Wrong banknote nominal value");
            }
            Cell cell = cells.get(banknote.getValue());
            cell.add(banknote);
        }
    }

    public HashMap<Integer, List<Banknote>> withdraw(int amount) {
        HashMap<Integer, List<Banknote>> banknotes = new HashMap<>();
        int dynamicAmount = amount;
        for (Integer nominal : cells.keySet()) {
            int count = dynamicAmount / nominal;
            Cell cell = cells.get(nominal);
            banknotes.put(nominal, cell.withdraw(count));
            dynamicAmount = dynamicAmount % nominal;
        }

        if (dynamicAmount > 0) {
            throw new RuntimeException("Wrong requested amount");
        }

        return banknotes;
    }

    public int getBalance() {
        int balance = 0;
        for (Cell cell : cells.values()) {
            balance += cell.getValue();
        };

        return balance;
    }
}
