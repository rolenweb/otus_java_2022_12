package ru.otus.processor;

import ru.otus.model.Message;
import java.time.LocalDateTime;

public class ProcessorThrowException implements Processor {

    private int evenSecond;

    @Override
    public Message process(Message message) {
        var startSecond = LocalDateTime.now().getSecond();
        var second = 0;
        while (second < 10) {
            if (LocalDateTime.now().getSecond() - startSecond == second) {
                continue;
            }
            second = LocalDateTime.now().getSecond() - startSecond;
            if ( second % 2 == 0 ){
                evenSecond = second;
                throw new EvenNumberException();
            }
        }
        return null;
    }

    public int getEvenSecond() {
        return evenSecond;
    }
}
