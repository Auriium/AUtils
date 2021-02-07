package com.elytraforce.aUtils.core.database;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Hikari implementation of a datakore
 */
public class HikariKore extends ADataKore {

    private final HikariDataSource source;
    private final ThreadPoolExecutor asyncQueue;

    public HikariKore(String databaseName, ALogger logger, HikariConfig config) {
        super(databaseName, logger);

        int numOfThreads = 5;

        source = new HikariDataSource(config);

        logger.debug("Successfully created a HikariDataKore with the following info: \n"
                + "Jdbc URL: " + config.getJdbcUrl() + "\n"
                + "Username: " + config.getUsername() + "\n"
                + "Password: " + config.getPassword() + "\n"
                + "Properties: " + config.getDataSourceProperties());
        asyncQueue = (ThreadPoolExecutor) Executors.newFixedThreadPool(numOfThreads);
    }

    @Override
    Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public void close() {
        asyncQueue.shutdown();
        try {
            asyncQueue.awaitTermination(15000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        source.close();

        logger.info("HikariDataKore - Shutting Down! Goodbye!");
    }
}
