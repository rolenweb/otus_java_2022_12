package ru.otus.l12.homework;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        Atm atm = initAtm();
        System.out.println("Start balance: " + atm.getBalance());

        System.out.println("Deposit sum: 6800");
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(BanknoteFactory.create(5000));
        banknotes.add(BanknoteFactory.create(1000));
        banknotes.add(BanknoteFactory.create(500));
        banknotes.add(BanknoteFactory.create(200));
        banknotes.add(BanknoteFactory.create(100));
        atm.deposit(banknotes);
        System.out.println("balance: " + atm.getBalance());

        System.out.println("Withdraw sum: 256700");
        try {
            HashMap<Integer, List<Banknote>> withdrawalBanknotes = atm.withdraw(258800);
            for (Integer nominal : withdrawalBanknotes.keySet()) {
                System.out.println("Nominal: " + nominal + "; count: " +withdrawalBanknotes.get(nominal).size());
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }


        System.out.println("balance: " + atm.getBalance());
    }

    private static Atm initAtm() {
        Map<Integer, Cell> cells = new TreeMap<>();
        cells.put(100, new Cell(100, BanknoteFactory.createStack(100, 100)));
        cells.put(200, new Cell(200, BanknoteFactory.createStack(100, 200)));
        cells.put(500, new Cell(500, BanknoteFactory.createStack(100, 500)));
        cells.put(1000, new Cell(1000, BanknoteFactory.createStack(100, 1000)));
        cells.put(5000, new Cell(5000, BanknoteFactory.createStack(100, 5000)));

        return new Atm(cells);
    }
}
