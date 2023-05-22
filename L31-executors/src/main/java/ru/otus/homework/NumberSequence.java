package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberSequence {
    private static final Logger logger = LoggerFactory.getLogger(NumberSequence.class);
    private String activeThread = "SECOND";

    private void action(String thread) throws InterruptedException {
        var i = 1;
        printNumber(thread, i);
        while (i != 10) {
            i++;
            printNumber(thread, i);
        }
        while (i > 1){
            i--;
            printNumber(thread, i);
        }
    }

    private synchronized void printNumber(String thread, Integer number) throws InterruptedException {
        while (activeThread.equals(thread)) {
            this.wait();
        }
        activeThread = thread;
        logger.info(String.valueOf(number));
        notifyAll();
    }

    public static void main(String[] args) {
        NumberSequence numberSequence = new NumberSequence();
        new Thread(() -> {
            try {
                numberSequence.action("FIRST");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                numberSequence.action("SECOND");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
