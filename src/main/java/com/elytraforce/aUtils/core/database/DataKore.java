package com.elytraforce.aUtils.core.database;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.zaxxer.hikari.HikariConfig;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Datakore system with MySQL and SQLite support via a builder.
 * Do not instantiate, please use the builder.
 */
public abstract class DataKore {

    protected final ALogger logger;
    protected final String databaseName;
    
    protected DataKore(String databaseName, ALogger logger) {
        this.databaseName = databaseName;
        this.logger = logger;
    }

    abstract Connection getConnection() throws SQLException;
    public abstract void close();

    public String getDatabaseName() {
        return databaseName;
    }

    public CompletableFuture<Void> update(String sql, Object... toSet) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    if (toSet != null) {
                        for (int i = 0; i < toSet.length; i++) {
                            statement.setObject(i + 1, toSet[i]);
                        }
                    }
                    statement.executeUpdate();
                    return null;
                }
            } catch (SQLException e) {
                throw new CompletionException(e);
            }
        });
    }

    public CachedRowSet query(String sql, Object... toSet) throws SQLException{
        CachedRowSet set;

        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            if (toSet != null) {
                for (int i = 0; i < toSet.length; i++) {
                    statement.setObject(i + 1, toSet[i]);
                }
            }

            ResultSet result = statement.executeQuery();
            set = RowSetProvider.newFactory().createCachedRowSet();
        }

        return set;
    }

    public int createTableFromFile(String file, Class<?> mainClass) throws SQLException, IOException {
        URL resource = Resources.getResource(mainClass, "/" + file);
        String databaseStructure = Resources.toString(resource, Charsets.UTF_8);
        logger.debug("(Create Table) Successfully loaded an SQL statement from the " + file + " file.");
        return createTableFromStatement(databaseStructure);
    }
    public int createTableFromStatement(String sql) throws SQLException {
        try (Connection connection = getConnection()) {
            logger.debug("(Create Table) Successfully got a new connection from hikari: " + connection.toString() + ", catalog: " + connection.getCatalog());
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                logger.debug("(Create Table) Successfully created a PreparedStatement. Executing the following: " + sql);
                return statement.executeUpdate();
            }
        }
    }
    public void createTableFromSchema(String file) throws SQLException {
        try (final Connection connection = getConnection()) {
            String beginning = "USE " + getDatabaseName() + ";";
            InputStream databaseSchema = DeprecatedDataKore.class.getResourceAsStream(file);
            List<InputStream> streams = Arrays.asList(
                    new ByteArrayInputStream(beginning.getBytes()),
                    databaseSchema);
            InputStream schema = new SequenceInputStream(Collections.enumeration(streams));

            IbatisScriptRunner runner = new IbatisScriptRunner(connection);
            runner.setLogWriter(null);
            runner.runScript(new InputStreamReader(schema));
        }
    }

    public static class Builder {

        private String pluginName;
        private String name;
        private String username;
        private String password;
        private int port;
        private String host;
        private boolean useSSL;
        private ALogger logger;
        private boolean useSQLite;

        public Builder(ALogger logger) {
            this.pluginName = "defaultPlugin";
            this.name = "defaultDatabase";
            this.username = "defaultUser";
            this.password = "defaultPass";
            this.port = 3306;
            this.host = "defaultHost";
            this.useSSL = false;
            this.logger = logger;
            this.useSQLite = false;
        }

        public Builder setPluginName(String string) {
            this.pluginName = string;
            return this;
        }

        public Builder setUserName(String string) {
            this.username = string;
            return this;
        }

        public Builder setDatabaseName(String string) {
            this.name = string;
            return this;
        }

        public Builder setPassword(String string) {
            this.password = string;
            return this;
        }

        public Builder setPort(int num) {
            this.port = num;
            return this;
        }

        public Builder setHost(String string) {
            this.host = string;
            return this;
        }

        public Builder useSSL(Boolean b) {
            this.useSSL = b;
            return this;
        }

        public Builder useSQLite(Boolean b) {
            this.useSQLite = b;
            return this;
        }

        public DataKore build() {

            if (useSQLite) {
                return new SQLiteDataKore(this.name,this.logger);
            } else {
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(String.format(useSSL ? "jdbc:mysql://%s:%d/%s" : "jdbc:mysql://%s:%d/%s?useSSL=false",
                        host,
                        port,
                        name));
                config.setUsername(username);
                config.setPassword(password);
                config.setPoolName(pluginName + "-datakore-hikari");

                config.addDataSourceProperty("cachePrepStmts", true);
                config.addDataSourceProperty("prepStmtCacheSize", 250);
                config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
                config.addDataSourceProperty("useServerPrepStmts", true);
                config.addDataSourceProperty("useLocalSessionState", true);
                config.addDataSourceProperty("rewriteBatchedStatements", true);
                config.addDataSourceProperty("cacheResultSetMetadata", true);
                config.addDataSourceProperty("cacheServerConfiguration", true);
                config.addDataSourceProperty("elideSetAutoCommit", true);
                config.addDataSourceProperty("maintainTimeStats", false);
                return new HikariDataKore(this.name,this.logger,config);
            }


        }
    }

}
