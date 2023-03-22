package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            Writer writer = new FileWriter(fileName);
            new Gson().toJson(data, writer);
            writer.close();
        } catch (JsonIOException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
