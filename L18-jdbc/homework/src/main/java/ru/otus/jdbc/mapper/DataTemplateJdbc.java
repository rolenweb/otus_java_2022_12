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
                    List<Object> listValues = new ArrayList<>();
                    List<Class<T>> listTypes = new ArrayList<>();
                    List<Field> fields = entityClassMetaData.getAllFields();
                    fields.forEach(field -> {
                        try {
                            if (!(rs.getObject(field.getName()) == null)) {
                                listValues.add(rs.getObject(field.getName(), field.getType()));
                                listTypes.add((Class<T>) field.getType());
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    return (T)entityClassMetaData.getConstructor(listTypes.toArray(Class[]::new)).newInstance(listValues.toArray());
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var list = new ArrayList<T>();
            try {
                while (rs.next()) {
                    List<Object> fieldValues = new ArrayList<>();
                    List<Class<T>> fieldTypes = new ArrayList<>();
                    List<Field> fields = entityClassMetaData.getAllFields();
                    fields.forEach(field -> {
                        try {
                            fieldValues.add(rs.getObject(field.getName(), field.getType()));
                            fieldTypes.add((Class<T>) field.getType());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    list.add((T)entityClassMetaData.getConstructor(fieldTypes.toArray(Class[]::new)).newInstance(fieldValues.toArray()));
                }
                return list;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
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
                    throw new RuntimeException(e);
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
