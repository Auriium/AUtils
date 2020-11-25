package com.elytraforce.aUtils.database;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

@SuppressWarnings("unused")
public class Database {
    private final HikariConfig config;
    private HikariDataSource source;
    private JavaPlugin using;
    private Logger logger;
    private int numOfThreads;
    private long maxBlockTime;
    private boolean debugLoggingEnabled;
    private ThreadPoolExecutor asyncQueue;
    private boolean shuttingDown;

    private Database() {
        this.config = new HikariConfig();
        this.numOfThreads = 5;
        this.maxBlockTime = 15000L;
    }

    public static Database newDatabase() {
        return new Database();
    }

    public Database withConnectionInfo(final String host, final int port, final String database) {
        return this.withConnectionInfo(host, port, database, true);
    }

    public Database withConnectionInfo(final String host, final int port, final String database, final boolean useSSL) {
        this.config.setJdbcUrl(String.format(useSSL ? "jdbc:mysql://%s:%d/%s" : "jdbc:mysql://%s:%d/%s?useSSL=false", host, port, database));
        return this;
    }

    public Database withUsername(final String username) {
        this.config.setUsername(username);
        return this;
    }

    public Database withPassword(final String password) {
        this.config.setPassword(password);
        return this;
    }

    public Database withDefaultProperties() {
        this.config.addDataSourceProperty("cachePrepStmts", true);
        this.config.addDataSourceProperty("prepStmtCacheSize", 250);
        this.config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.config.addDataSourceProperty("useServerPrepStmts", true);
        this.config.addDataSourceProperty("useLocalSessionState", true);
        this.config.addDataSourceProperty("rewriteBatchedStatements", true);
        this.config.addDataSourceProperty("cacheResultSetMetadata", true);
        this.config.addDataSourceProperty("cacheServerConfiguration", true);
        this.config.addDataSourceProperty("elideSetAutoCommit", true);
        this.config.addDataSourceProperty("maintainTimeStats", false);
        return this;
    }

    public Database withDataSourceProperty(final String property, final Object value) {
        this.config.addDataSourceProperty(property, value);
        return this;
    }

    public Database withPluginUsing(final JavaPlugin plugin) {
        this.using = plugin;
        return this;
    }

    public Database withNumOfThreads(final int numOfThreads) {
        this.numOfThreads = numOfThreads;
        return this;
    }

    public Database withMaxBlockTime(final long maxBlockTime) {
        this.maxBlockTime = maxBlockTime;
        return this;
    }

    public Database withDebugLogging() {
        this.debugLoggingEnabled = true;
        return this;
    }

    public Database open() {
        this.logger = LoggerFactory.getLogger(this.using.getName() + " - Database");
        this.config.setPoolName(this.using.getName().toLowerCase() + "-database-hikari");
        this.source = new HikariDataSource(this.config);
        this.debug("Successfully created a HikariDataSource with the following info: \nJdbc URL: " + this.config.getJdbcUrl() + "\nUsername: " + this.config.getUsername() + "\nPassword: " + this.config.getPassword() + "\nProperties: " + this.config.getDataSourceProperties());
        this.asyncQueue = (ThreadPoolExecutor)Executors.newFixedThreadPool(this.numOfThreads);
        return this;
    }

    public HikariDataSource getDataSource() {
        if (this.source == null) {
            throw new IllegalArgumentException("The data source has not been instantiated! The database must be opened first.");
        }
        return this.source;
    }

    public void close() {
        this.shuttingDown = true;
        this.asyncQueue.shutdown();
        try {
            this.asyncQueue.awaitTermination(this.maxBlockTime, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e) {
            this.logger.error("Thread was interrupted while waiting for SQL statements to finish executing!");
            e.printStackTrace();
        }
        this.source.close();
    }

    public boolean isClosed() {
        return this.source.isClosed();
    }

    public int createTableFromFile(final String file, final Class<?> mainClass) throws IOException, SQLException {
        final URL resource = Resources.getResource(mainClass, "/" + file);
        final String databaseStructure = Resources.toString(resource, Charsets.UTF_8);
        this.debug("(Create Table) Successfully loaded an SQL statement from the " + file + " file.");
        return this.createTableFromStatement(databaseStructure);
    }

    public int createTableFromStatement(final String sql) throws SQLException {
        try (final Connection connection = this.source.getConnection()) {
            this.debug("(Create Table) Successfully got a new connection from hikari: " + connection.toString() + ", catalog: " + connection.getCatalog());
            try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                this.debug("(Create Table) Successfully created a PreparedStatement. Executing the following: " + sql);
                return statement.executeUpdate();
            }
        }
    }

    public ResultSet query(String sql, Object[] toSet) throws SQLException {
        try (Connection connection = source.getConnection()) {
            debug("(Query) Successfully got a new connection from hikari: " + connection.toString() + ", catalog: " + connection.getCatalog());
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                debug("(Query) Successfully created a PreparedStatement with the following: " + sql);
                if (toSet != null) {
                    for (int i = 0; i < toSet.length; i++) {
                        statement.setObject(i + 1, toSet[i]);
                    }
                }
                debug("(Query) Successfully set objects. Executing the following: " + statement.toString().substring(statement.toString().indexOf('-') + 1));
                ResultSet result = statement.executeQuery();
                return result;
            }
        }
    }

    public void queryAsync(String sql, Object[] toSet, Callback<ResultSet> callback) {
        asyncQueue.execute(() -> {
            try (Connection connection = source.getConnection()) {
                debug("(Query) Successfully got a new connection from hikari: " + connection.toString() + ", catalog: " + connection.getCatalog());
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    debug("(Query) Successfully created a PreparedStatement with the following: " + sql);
                    if (toSet != null) {
                        for (int i = 0; i < toSet.length; i++) {
                            statement.setObject(i + 1, toSet[i]);
                        }
                    }
                    debug("(Query) Successfully set objects. Executing the following: " + statement.toString().substring(statement.toString().indexOf('-') + 1));
                    ResultSet result = statement.executeQuery();
                    if (!shuttingDown) {
                        RunnableFuture<Void> task = new FutureTask<>(() -> {
                            try {
                                callback.callback(result);
                            } catch (SQLException e) {
                                logger.error("There was an error while reading the query result!");
                                e.printStackTrace();
                            }
                            return null;
                        });
                        Bukkit.getScheduler().runTask(using, task);
                        try {
                            task.get();
                        } catch (InterruptedException | ExecutionException e) {
                            logger.error("There was an error while waiting for the query callback to complete!");
                            e.printStackTrace();
                        }
                        result.close();
                    } else {
                        try {
                            logger.warn("SQL statement executed asynchronously during shutdown, so the synchronous callback was not run. This occurred during a query, so data will not be loaded.");
                            result.close();
                        } catch (SQLException ignored) {}
                    }
                } catch (SQLException e) {
                    logger.error("There was an error when querying the database!");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                logger.error("There was an error when querying the database!");
                logger.error("Error occurred on the following SQL statement: " + sql);
                e.printStackTrace();
            }
        });
    }

    public int update(final String sql, final Object[] toSet) throws SQLException {
        try (final Connection connection = this.source.getConnection()) {
            this.debug("(Update) Successfully got a new connection from hikari: " + connection.toString() + ", catalog: " + connection.getCatalog());
            try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                this.debug("(Update) Successfully created a PreparedStatement with the following: " + sql);
                for (int i = 0; i < toSet.length; ++i) {
                    statement.setObject(i + 1, toSet[i]);
                }
                this.debug("(Update) Successfully set objects. Executing the following: " + statement.toString().substring(statement.toString().indexOf(45) + 1));
                return statement.executeUpdate();
            }
        }
    }

    public void updateAsync(String sql, Object[] toSet, Callback<Integer> callback) {
        asyncQueue.execute(() -> {
            try {
                int toReturn = update(sql, toSet);
                if (!shuttingDown) {
                    RunnableFuture<Void> task = new FutureTask<>(() -> {
                        try {
                            callback.callback(toReturn);
                        } catch (SQLException ignored) {}
                        return null;
                    });
                    Bukkit.getScheduler().runTask(using, task);
                    try {
                        task.get();
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error("There was an error while waiting for the update callback to complete!");
                        e.printStackTrace();
                    }
                } else {
                    logger.warn("SQL statement executed asynchronously during shutdown, so the synchronous callback was not run. This occurred during an update, so no data loss has occurred.");
                }
            } catch (SQLException e) {
                logger.error("There was an error when updating the database!");
                logger.error("Error occurred on the following SQL statement: " + sql);
                e.printStackTrace();
            }
        });
    }

    private void debug(final String message) {
        if (this.debugLoggingEnabled) {
            this.logger.info(message);
        }
    }
}
