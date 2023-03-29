package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    private final EntityClassMetaData<?> entityClassMetaData;
    private final HashMap<SqlQuery, String> queries = new HashMap<>();

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        this.queries.put(SqlQuery.SELECT_ALL, this.formSelectAllSql());
        this.queries.put(SqlQuery.SELECT_BY_ID, this.formSelectByIdSql());
        this.queries.put(SqlQuery.INSERT, this.formInsertSql());
        this.queries.put(SqlQuery.UPDATE, this.formUpdateSql());
    }

    @Override
    public String getSelectAllSql() {
        return queries.get(SqlQuery.SELECT_ALL);
    }

    @Override
    public String getSelectByIdSql() {
        return queries.get(SqlQuery.SELECT_BY_ID);
    }

    @Override
    public String getInsertSql() {
        return queries.get(SqlQuery.INSERT);
    }

    @Override
    public String getUpdateSql() {
        return queries.get(SqlQuery.UPDATE);
    }

    private String getAllColumns() {
        List<Field> fields = entityClassMetaData.getAllFields();
        return fields.stream().map(Field::getName).collect(Collectors.joining(", "));
    }

    private String formSelectAllSql() {
        return String.format("select %s from %s", getAllColumns(), entityClassMetaData.getName());
    }

    private String formSelectByIdSql() {
        return String.format(
                "select %s from %s where %s = ?",
                getAllColumns(),
                entityClassMetaData.getName(),
                entityClassMetaData.getIdField().getName()
        );
    }

    private String formInsertSql() {
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

    private String formUpdateSql() {
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
