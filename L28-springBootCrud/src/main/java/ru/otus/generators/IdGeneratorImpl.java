package ru.otus.generators;

import org.springframework.stereotype.Service;

@Service
public class IdGeneratorImpl implements IdGenerator{
    @Override
    public String generate() {
        return "c:" + System.currentTimeMillis();
    }
}
