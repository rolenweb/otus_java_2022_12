package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s", entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        List<Field> fields = entityClassMetaData.getAllFields();
        String columns = fields.stream().map(Field::getName).collect(Collectors.joining(", "));
        return String.format(
                "select %s from %s where %s = ?",
                columns, entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName()
        );
    }

    @Override
    public String getInsertSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        String columns = fields.stream().map(Field::getName).collect(Collectors.joining(", "));
        String values = fields.stream().map(field -> "?").collect(Collectors.joining(", "));
        return String.format(
                "insert into %s(%s) values (%s)",
                entityClassMetaData.getName(),
                columns,
                values
        );
    }

    @Override
    public String getUpdateSql() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        String columns = fields.stream().map(Field::getName).map(f -> String.format("%s = ?", f)).collect(Collectors.joining(", "));
        return String.format(
                "update %s set %s where %s = ?",
                entityClassMetaData.getName(),
                columns,
                entityClassMetaData.getIdField().getName()
        );
    }
}
