package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityClassMetaDataImplTest {

    @Test
    @DisplayName("Can get a name from a class")
    void testCanGetName() {
        assertEquals("client", new EntityClassMetaDataImpl<>(Client.class).getName());
        assertEquals("manager", new EntityClassMetaDataImpl<>(Manager.class).getName());
    }

    @Test
    @DisplayName("Can get all class fields")
    void testCanGetAllFields() {
        var clientListField = new EntityClassMetaDataImpl<>(Client.class).getAllFields();
        assertEquals(1, clientListField.stream().filter(field -> field.getName().equals("id")).count());
        assertEquals(1, clientListField.stream().filter(field -> field.getName().equals("name")).count());

        var managerListField = new EntityClassMetaDataImpl<>(Manager.class).getAllFields();
        assertEquals(1, managerListField.stream().filter(field -> field.getName().equals("no")).count());
        assertEquals(1, managerListField.stream().filter(field -> field.getName().equals("label")).count());
        assertEquals(1, managerListField.stream().filter(field -> field.getName().equals("param1")).count());
    }

    @Test
    @DisplayName("Can get id field")
    void testCanGetIdField() {
        var clientIdField = new EntityClassMetaDataImpl<>(Client.class).getIdField();
        assertEquals("id",clientIdField.getName());

        var managerIdField = new EntityClassMetaDataImpl<>(Manager.class).getIdField();
        assertEquals("no",managerIdField.getName());
    }

    @Test
    @DisplayName("Can get fields except id")
    void testCanGetFieldWithoutId() {
        var clientFields = new EntityClassMetaDataImpl<>(Client.class).getFieldsWithoutId();
        assertEquals(0, clientFields.stream().filter(field -> field.getName().equals("id")).count());
        assertEquals(1, clientFields.stream().filter(field -> field.getName().equals("name")).count());

        var managerFields = new EntityClassMetaDataImpl<>(Manager.class).getFieldsWithoutId();
        assertEquals(0, managerFields.stream().filter(field -> field.getName().equals("no")).count());
        assertEquals(1, managerFields.stream().filter(field -> field.getName().equals("label")).count());
        assertEquals(1, managerFields.stream().filter(field -> field.getName().equals("param1")).count());
    }
}
