package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitySQLMetaDataImplTest {
    @Test
    @DisplayName("Can get SQL query to get all records")
    void testCanGetSelectAllSql() {
        var clientEntitySqlData = new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<>(Client.class));
        var expectedClientSql = "select id, name from client";
        var actualClientSql = clientEntitySqlData.getSelectAllSql();
        assertEquals(expectedClientSql, actualClientSql);

        var managerEntitySqlData = new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<>(Manager.class));
        var expectedManagerSql = "select no, label, param1 from manager";
        var actualManagerSql = managerEntitySqlData.getSelectAllSql();
        assertEquals(expectedManagerSql, actualManagerSql);
    }

    @Test
    @DisplayName("Cen get SQL query to get record by ID")
    void testCanGetSelectByIdSql() {
        var clientEntitySqlData = new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<>(Client.class));
        var expectedClientSql = "select id, name from client where id = ?";
        var actualClientSql = clientEntitySqlData.getSelectByIdSql();
        assertEquals(expectedClientSql, actualClientSql);

        var managerEntitySqlData = new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<>(Manager.class));
        var expectedManagerSql = "select no, label, param1 from manager where no = ?";
        var actualManagerSql = managerEntitySqlData.getSelectByIdSql();
        assertEquals(expectedManagerSql, actualManagerSql);
    }

    @Test
    @DisplayName("Can get SQL query to insert a data")
    void testCanGetInsertSql() {
        var clientEntityMetaData = new EntityClassMetaDataImpl<>(Client.class);
        var clientEntitySqlData = new EntitySQLMetaDataImpl(clientEntityMetaData);
        var expectedClientSql = "insert into client(name) values (?)";
        var actualClientSql = clientEntitySqlData.getInsertSql();
        assertEquals(expectedClientSql, actualClientSql);

        var managerEntityClassMetaData = new EntityClassMetaDataImpl<>(Manager.class);
        var managerEntitySqlData = new EntitySQLMetaDataImpl(managerEntityClassMetaData);
        var expectedManagerSql = "insert into manager(label, param1) values (?, ?)";
        var actualManagerSql = managerEntitySqlData.getInsertSql();
        assertEquals(expectedManagerSql, actualManagerSql);
    }

    @Test
    @DisplayName("Can get SQL query to update a data")
    void testCanGetUpdateSql() {
        var clientEntitySqlData = new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<>(Client.class));
        var expectedClientSql = "update client set name = ? where id = ?";
        var actualClientSql = clientEntitySqlData.getUpdateSql();
        assertEquals(expectedClientSql, actualClientSql);

        var managerEntitySqlData = new EntitySQLMetaDataImpl(new EntityClassMetaDataImpl<>(Manager.class));
        var expectedManagerSql = "update manager set label = ?, param1 = ? where no = ?";
        var actualManagerSql = managerEntitySqlData.getUpdateSql();
        assertEquals(expectedManagerSql, actualManagerSql);
    }
}
