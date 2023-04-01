package ru.otus.jdbc.mapper;

import ru.otus.crm.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T>{
    private final Class<T> clazz;
    private final String name;
    private final List<Field> fields;
    private final Field idField;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
        //It will work correctly only for simple names without camel cases. I think the best way to crate the annotation @table
        this.name = clazz.getSimpleName().toLowerCase();
        this.fields = Arrays.stream(clazz.getDeclaredFields()).toList();
        this.idField = this.fields.stream().filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElseThrow();
        this.fieldsWithoutId = this.fields.stream().filter(field -> !field.isAnnotationPresent(Id.class)).toList();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
