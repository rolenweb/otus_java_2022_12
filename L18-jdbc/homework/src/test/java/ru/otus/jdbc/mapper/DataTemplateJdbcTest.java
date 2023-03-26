package ru.otus.jdbc.mapper;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class DataTemplateJdbcTest {
    private HikariDataSource dataSourcePool;

    // will be started before and stopped after each test method
    @Container
    private final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:12-alpine")
            .withDatabaseName("testDataBase")
            .withUsername("owner")
            .withPassword("secret")
            .withClasspathResourceMapping("00_createTables.sql", "/docker-entrypoint-initdb.d/00_createTables.sql", BindMode.READ_ONLY)
            .withClasspathResourceMapping("01_insertData.sql", "/docker-entrypoint-initdb.d/01_insertData.sql", BindMode.READ_ONLY);

    @Test
    @DisplayName("Can find an entity by ID")
    void testCanFindById() {
        makeConnectionPool();

        try (Connection connection = getConnection()) {
            var dbExecutor = new DbExecutorImpl();
            EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
            EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
            var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient);
            Optional<Client> client = dataTemplateClient.findById(connection,1L);
            assertInstanceOf(Client.class, client.get());
            assertEquals(1, client.get().getId());
            assertEquals("name1", client.get().getName());

            EntityClassMetaData<Manager> entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class);
            EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);
            var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataManager, entityClassMetaDataManager);
            Optional<Manager> manager = dataTemplateManager.findById(connection,1L);
            assertInstanceOf(Manager.class, manager.get());
            assertEquals(1, manager.get().getNo());
            assertEquals("l1", manager.get().getLabel());
            assertNull(manager.get().getParam1());

            Optional<Manager> manager2 = dataTemplateManager.findById(connection,2L);
            assertInstanceOf(Manager.class, manager2.get());
            assertEquals(2, manager2.get().getNo());
            assertEquals("l2", manager2.get().getLabel());
            assertEquals("p2", manager2.get().getParam1());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dataSourcePool.close();
    }

    @Test
    @DisplayName("Can find all entities")
    void testCanFindAll() {
        makeConnectionPool();

        try (Connection connection = getConnection()) {
            var dbExecutor = new DbExecutorImpl();
            EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
            EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
            var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient);
            var clients = dataTemplateClient.findAll(connection);
            assertEquals(5, clients.size());

            EntityClassMetaData<Manager> entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class);
            EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);
            var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataManager, entityClassMetaDataManager);
            var managers = dataTemplateManager.findAll(connection);
            assertEquals(3, managers.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dataSourcePool.close();
    }

    @Test
    @DisplayName("Can insert an entity")
    void testInsert() {
        makeConnectionPool();

        try (Connection connection = getConnection()) {
            var dbExecutor = new DbExecutorImpl();
            EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Client.class);
            EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
            var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient);
            Client client = new Client("name6");
            dataTemplateClient.insert(connection, client);
            assertEquals("name6", dataTemplateClient.findById(connection,6L).get().getName());

            EntityClassMetaData entityClassMetaDataManager = new EntityClassMetaDataImpl(Manager.class);
            EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);
            var dataTemplateManager = new DataTemplateJdbc<Manager>(dbExecutor, entitySQLMetaDataManager, entityClassMetaDataManager);
            Manager manager = new Manager("l4");
            dataTemplateManager.insert(connection, manager);
            assertEquals("l4", dataTemplateManager.findById(connection, 4L).get().getLabel());
            assertNull(dataTemplateManager.findById(connection, 4L).get().getParam1());

            Manager manager2 = new Manager("l5");
            manager2.setParam1("p5");
            dataTemplateManager.insert(connection, manager2);
            assertEquals("l5", dataTemplateManager.findById(connection, 5L).get().getLabel());
            assertEquals("p5", dataTemplateManager.findById(connection, 5L).get().getParam1());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dataSourcePool.close();
    }

    @Test
    @DisplayName("Can update an entity")
    void testUpdate() {
        makeConnectionPool();

        try (Connection connection = getConnection()) {
            var dbExecutor = new DbExecutorImpl();
            EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Client.class);
            EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
            var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient);
            Client client = dataTemplateClient.findById(connection, 1L).get();
            client.setName("updatedName");
            dataTemplateClient.update(connection, client);
            assertEquals("updatedName", dataTemplateClient.findById(connection,1L).get().getName());

            EntityClassMetaData entityClassMetaDataManager = new EntityClassMetaDataImpl(Manager.class);
            EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);
            var dataTemplateManager = new DataTemplateJdbc<Manager>(dbExecutor, entitySQLMetaDataManager, entityClassMetaDataManager);
            Manager manager = dataTemplateManager.findById(connection, 1L).get();
            manager.setLabel("updatedLabel");
            dataTemplateManager.update(connection, manager);
            assertEquals("updatedLabel", dataTemplateManager.findById(connection, 1L).get().getLabel());

            Manager manager2 = dataTemplateManager.findById(connection, 1L).get();
            manager2.setLabel("updatedLabel");
            manager2.setParam1("updatedParam1");
            dataTemplateManager.update(connection, manager2);
            Manager updatedManager2 = dataTemplateManager.findById(connection, 1L).get();
            assertEquals("updatedLabel", updatedManager2.getLabel());
            assertEquals("updatedParam1", updatedManager2.getParam1());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        dataSourcePool.close();
    }

    private Connection makeSingleConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(postgresqlContainer.getJdbcUrl(), getConnectionProperties());
        connection.setAutoCommit(false);
        return connection;
    }


    private Properties getConnectionProperties() {
        Properties props = new Properties();
        props.setProperty("user", postgresqlContainer.getUsername());
        props.setProperty("password", postgresqlContainer.getPassword());
        props.setProperty("ssl", "false");
        return props;
    }

    void makeConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgresqlContainer.getJdbcUrl());
        config.setConnectionTimeout(3000); //ms
        config.setIdleTimeout(60000); //ms
        config.setMaxLifetime(600000);//ms
        config.setAutoCommit(false);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setPoolName("DemoHiPool");
        config.setRegisterMbeans(true);

        config.setDataSourceProperties(getConnectionProperties());

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSourcePool = new HikariDataSource(config);
    }

    private Connection getConnection() throws SQLException {
        return dataSourcePool.getConnection();
    }
}
