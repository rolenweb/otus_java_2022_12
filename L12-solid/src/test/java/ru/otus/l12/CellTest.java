package ru.otus.l12;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.l12.homework.Banknote;
import ru.otus.l12.homework.Cell;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Stack;

public class CellTest {

    @Test
    @DisplayName("Test that a cell can be created")
    public void canCreateCell() {
        Stack<Banknote> banknotes = new Stack<>();
        banknotes.push(new Banknote(100));
        banknotes.push(new Banknote(100));
        banknotes.push(new Banknote(100));
        banknotes.push(new Banknote(100));
        Cell cell = new Cell(100, banknotes);

        assertEquals(400, cell.getValue());
    }

    @Test
    @DisplayName("Test that nominal cell value must be equal to nominal banknote values")
    public void throwExceptionIfCreateCellWithWrongBanknoteNominalValues() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Stack<Banknote> banknotes = new Stack<>();
            banknotes.push(new Banknote(100));
            banknotes.push(new Banknote(200));
            new Cell(100, banknotes);
        });

        String expectedMessage = "Nominal value must be 100";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test that a banknote can be added to a cell")
    public void canAddBanknoteToCell() {
        Stack<Banknote> banknotes = new Stack<>();
        Cell cell = new Cell(100, banknotes);
        cell.add(new Banknote(100));

        assertEquals(100, cell.getValue());
    }

    @Test
    @DisplayName("Test that the wrong nominal banknote value cannot be added")
    public void throwExceptionWhenAddWrongNominalBanknoteValue() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Stack<Banknote> banknotes = new Stack<>();
            Cell cell = new Cell(100, banknotes);
            cell.add(new Banknote(200));
        });

        String expectedMessage = "Nominal value must be 100";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test that banknotes can be withdraw")
    public void canWithdrawBanknote() {
        Stack<Banknote> banknotes = new Stack<>();
        banknotes.push(new Banknote(100));
        banknotes.push(new Banknote(100));
        banknotes.push(new Banknote(100));
        Cell cell = new Cell(100, banknotes);

        List<Banknote> withdrawingBanknotes = cell.withdraw(2);

        assertEquals(2, withdrawingBanknotes.size());
    }

    @Test
    @DisplayName("Test that banknotes cannot be withdrawn if there is not enough")
    public void throwExceptionIfCountWithdrawingBanknotesGreaterThanCountBanknoteInCell() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Stack<Banknote> banknotes = new Stack<>();
            banknotes.push(new Banknote(100));
            banknotes.push(new Banknote(100));
            banknotes.push(new Banknote(100));
            Cell cell = new Cell(100, banknotes);
            cell.withdraw(4);
        });

        String expectedMessage = "Not enough banknotes with a nominal 100";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
