package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    var instance = entityClassMetaData.getConstructor().newInstance();
                    List<Field> fields = entityClassMetaData.getAllFields();
                    fields.forEach(field -> {
                        try {
                            if (!(rs.getObject(field.getName()) == null)) {
                                var setter = String.format("set%s", StringUtil.capitalize(field.getName()));
                                var method = instance.getClass().getDeclaredMethod(setter, field.getType());
                                method.invoke(instance, rs.getObject(field.getName(), field.getType()));
                            }
                        } catch (SQLException | NoSuchMethodException | InvocationTargetException |
                                 IllegalAccessException e) {
                            throw new DataTemplateException(e);
                        }
                    });

                    return instance;
                }
                return null;
            } catch (SQLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var list = new ArrayList<T>();
            try {
                while (rs.next()) {
                    var instance = entityClassMetaData.getConstructor().newInstance();
                    List<Field> fields = entityClassMetaData.getAllFields();
                    fields.forEach(field -> {
                        try {
                            var setter = String.format("set%s", StringUtil.capitalize(field.getName()));
                            var method = instance.getClass().getDeclaredMethod(setter, field.getType());
                            method.invoke(instance, rs.getObject(field.getName(), field.getType()));
                        } catch (SQLException | InvocationTargetException | NoSuchMethodException |
                                 IllegalAccessException e) {
                            throw new DataTemplateException(e);
                        }
                    });
                    list.add(instance);
                }
                return list;
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            List<Field> fields = entityClassMetaData.getFieldsWithoutId();
            List<Object> listValues = new ArrayList<>();
            fields.forEach(field -> {
                try {
                    field.setAccessible(true);
                    listValues.add(field.get(client));
                } catch (IllegalAccessException e) {
                    throw new DataTemplateException(e);
                }
            });
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), listValues);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            var sql = entitySQLMetaData.getUpdateSql();
            List<Field> fields = entityClassMetaData.getFieldsWithoutId();
            List<Object> listValues = new ArrayList<>();
            fields.forEach(field -> {
                try {
                    field.setAccessible(true);
                    listValues.add(field.get(client));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            var idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            var idValue = idField.get(client);
            listValues.add(idValue);
            dbExecutor.executeStatement(connection, sql, listValues);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
