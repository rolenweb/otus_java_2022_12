package ru.otus.l12;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.l12.homework.Atm;
import ru.otus.l12.homework.Banknote;
import ru.otus.l12.homework.BanknoteFactory;
import ru.otus.l12.homework.Cell;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtmTest {
    @Test
    @DisplayName("Test that an atm can be created")
    public void canCreateAtm() {
        Map<Integer, Cell> cells = new TreeMap<>();
        cells.put(100, new Cell(100, BanknoteFactory.createStack(1, 100)));
        cells.put(200, new Cell(200, BanknoteFactory.createStack(1, 200)));
        cells.put(500, new Cell(500, BanknoteFactory.createStack(1, 500)));
        cells.put(1000, new Cell(1000, BanknoteFactory.createStack(1, 1000)));
        cells.put(5000, new Cell(5000, BanknoteFactory.createStack(1, 5000)));
        Atm atm = new Atm(cells);

        assertEquals(6800, atm.getBalance());
    }
    @Test
    @DisplayName("Test that an atm can be deposited")
    public void canDeposit() {
        Map<Integer, Cell> cells = new TreeMap<>();
        cells.put(100, new Cell(100, BanknoteFactory.createStack(0, 100)));
        cells.put(200, new Cell(200, BanknoteFactory.createStack(0, 200)));
        cells.put(500, new Cell(500, BanknoteFactory.createStack(0, 500)));
        cells.put(1000, new Cell(1000, BanknoteFactory.createStack(0, 1000)));
        cells.put(5000, new Cell(5000, BanknoteFactory.createStack(0, 5000)));
        Atm atm = new Atm(cells);
        ArrayList<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(100));
        banknotes.add(new Banknote(200));
        banknotes.add(new Banknote(500));
        banknotes.add(new Banknote(1000));
        banknotes.add(new Banknote(5000));
        atm.deposit(banknotes);

        assertEquals(6800, atm.getBalance());
    }

    @Test
    @DisplayName("Test that wrong nominal banknote values cannot be deposited")
    public void throwExceptionIfWrongNominalBanknoteValueIsDeposited() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Map<Integer, Cell> cells = new TreeMap<>();
            cells.put(100, new Cell(100, BanknoteFactory.createStack(0, 100)));
            Atm atm = new Atm(cells);
            ArrayList<Banknote> banknotes = new ArrayList<>();
            banknotes.add(new Banknote(10));
            atm.deposit(banknotes);
        });

        String expectedMessage = "Wrong banknote nominal value";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test that the requested sum can be withdrawn using min count of banknotes")
    public void canWithdrawUsingMinCountOfBanknotes(){
        Map<Integer, Cell> cells = new TreeMap<>();
        cells.put(100, new Cell(100, BanknoteFactory.createStack(100, 100)));
        cells.put(200, new Cell(200, BanknoteFactory.createStack(100, 200)));
        cells.put(500, new Cell(500, BanknoteFactory.createStack(100, 500)));
        cells.put(1000, new Cell(1000, BanknoteFactory.createStack(100, 1000)));
        cells.put(5000, new Cell(5000, BanknoteFactory.createStack(100, 5000)));
        Atm atm = new Atm(cells);
        HashMap<Integer, List<Banknote>> banknotes = atm.withdraw(257800);

        assertEquals(51, banknotes.get(5000).size());
        assertEquals(2, banknotes.get(1000).size());
        assertEquals(1, banknotes.get(500).size());
        assertEquals(1, banknotes.get(200).size());
        assertEquals(1, banknotes.get(100).size());
    }

    @Test
    @DisplayName("Test that the wrong requested sum cannot be withdrawn")
    public void throwExceptionIfRequestedSumIsNotCorrect() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Map<Integer, Cell> cells = new TreeMap<>();
            cells.put(100, new Cell(100, BanknoteFactory.createStack(100, 100)));
            cells.put(200, new Cell(200, BanknoteFactory.createStack(100, 200)));
            cells.put(500, new Cell(500, BanknoteFactory.createStack(100, 500)));
            cells.put(1000, new Cell(1000, BanknoteFactory.createStack(100, 1000)));
            cells.put(5000, new Cell(5000, BanknoteFactory.createStack(100, 5000)));
            Atm atm = new Atm(cells);
            atm.withdraw(247801);
        });

        String expectedMessage = "Wrong requested amount";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
