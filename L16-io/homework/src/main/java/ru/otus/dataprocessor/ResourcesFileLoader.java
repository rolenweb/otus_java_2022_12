package ru.otus.dataprocessor;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;
    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try {
            Type listOfMyClassObject = new TypeToken<ArrayList<Measurement>>() {}.getType();
            var gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("", "src", "test", "resources", fileName));
            List<Measurement> list = gson.fromJson(reader, listOfMyClassObject);
            reader.close();
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
