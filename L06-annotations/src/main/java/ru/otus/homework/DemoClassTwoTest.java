package ru.otus.homework;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

public class DemoClassTwoTest {
    @Before
    public void beforeEachOne() {
        System.out.println("beforeEachOne");
    }

    @Before
    public void beforeEachTwo() {
        throw new RuntimeException("Something happened in beforeEachTwo");
    }

    @Test
    public void testOne() {
        System.out.println("testOne");
    }

    @Test
    public void testTwo() {
        System.out.println("testTwo");
    }

    @Test
    public void testThree() {
        System.out.println("testThree");
    }

    @After
    public void afterEachOne() {
        System.out.println("afterEachOne");
    }

}
