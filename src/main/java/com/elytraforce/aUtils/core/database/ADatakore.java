package com.elytraforce.aUtils.core.database;

import com.elytraforce.aUtils.util.AUtil;
import com.elytraforce.aUtils.util.BUtil;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Provides;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;

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
import java.util.concurrent.*;

public class ADatakore {

    private final HikariDataSource source;
    private final int numOfThreads;
    private final long maxBlockTime;
    private final ThreadPoolExecutor asyncQueue;
    private boolean shuttingDown;

    private String databaseName;

    public boolean isClosed() {
        return source.isClosed();
    }

    private ADatakore(String databaseName, HikariConfig config, Logger logger) {
        config = new HikariConfig();
        numOfThreads = 5;
        maxBlockTime = 15000L;

        source = new HikariDataSource(config);

        AUtil.getUtils().debug("Successfully created a HikariDataSource with the following info: \n"
                + "Jdbc URL: " + config.getJdbcUrl() + "\n"
                + "Username: " + config.getUsername() + "\n"
                + "Password: " + config.getPassword() + "\n"
                + "Properties: " + config.getDataSourceProperties());
        asyncQueue = (ThreadPoolExecutor) Executors.newFixedThreadPool(numOfThreads);

        this.databaseName = databaseName;
    }

    public CompletableFuture<Void> update(String sql, Object... toSet) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = source.getConnection()) {
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

    public CompletableFuture<Void> updateMultiple(CompletableFuture<Void>... futures) {
        return CompletableFuture.allOf(futures);
    }

    public CachedRowSet query(String sql, Object... toSet) throws SQLException{
        CachedRowSet set;

        try(Connection connection = source.getConnection()) {
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

    public void close() {
        shuttingDown = true;
        asyncQueue.shutdown();
        try {
            asyncQueue.awaitTermination(maxBlockTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        source.close();
    }

    public int createTableFromFile(String file, Class<?> mainClass) throws IOException, SQLException {
        URL resource = Resources.getResource(mainClass, "/" + file);
        String databaseStructure = Resources.toString(resource, Charsets.UTF_8);
        //debug("(Create Table) Successfully loaded an SQL statement from the " + file + " file.");
        return createTableFromStatement(databaseStructure);
    }

    public int createTableFromStatement(String sql) throws SQLException {
        try (Connection connection = source.getConnection()) {
            //debug("(Create Table) Successfully got a new connection from hikari: " + connection.toString() + ", catalog: " + connection.getCatalog());
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                //debug("(Create Table) Successfully created a PreparedStatement. Executing the following: " + sql);
                return statement.executeUpdate();
            }
        }
    }

    public void createTablesFromSchema(String file, Class<?> mainClass) throws IOException, SQLException{
        try (final Connection connection = this.source.getConnection()) {
            String beginning = "USE " + this.databaseName + ";";
            InputStream databaseSchema = BUtil.getUtils().getPlugin().getResourceAsStream(file);
            List<InputStream> streams = Arrays.asList(
                    new ByteArrayInputStream(beginning.getBytes()),
                    databaseSchema);
            InputStream schema = new SequenceInputStream(Collections.enumeration(streams));

            ScriptRunner runner = new ScriptRunner(connection);
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
        private Logger logger;

        public Builder() {
            this.pluginName = "defaultPlugin";
            this.name = "defaultDatabase";
            this.username = "defaultUser";
            this.password = "defaultPass";
            this.port = 3306;
            this.host = "defaultHost";
            this.useSSL = false;
            this.logger = null;
        }

        public static Builder newInstance() {
            return new Builder();
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

        public Builder setLogger(Logger string) {
            this.logger = string;
            return this;
        }

        public ADatakore build() {
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

            return new ADatakore(name,config,logger);
        }

    }
}
