package ru.otus.homework;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

public class DemoClassTest {
    @Before
    public void beforeEachOne() {
        System.out.println("beforeEachOne");
    }
    @Before
    public void beforeEachTwo() {
        System.out.println("beforeEachTwo");
    }

    @Test
    public void testOne() {
        System.out.println("testOne");
    }

    @Test
    public void testTwo() {
        throw new RuntimeException("Something happened in testTwo");
    }

    @Test
    public void testThree() {
        System.out.println("testThree");
    }

    @After
    public void afterEachOne() {
        throw new RuntimeException("Something happened in afterEachOne");
    }

    @After
    public void afterEachTwo() {
        System.out.println("afterEachTwo");
    }
}
