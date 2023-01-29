package ru.otus.homework;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

public class DemoClassTwoTest {
    @Before
    public void beforeEachOne() {

    }

    @Before
    public void beforeEachTwo() {
        throw new RuntimeException("Something happened in beforeEachTwo");
    }

    @Test
    public void testOne() {

    }

    @Test
    public void testTwo() {

    }

    @Test
    public void testThree() {

    }

    @After
    public void afterEachOne() {

    }

}
