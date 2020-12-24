package com.elytraforce.aUtils.core.database;

import com.elytraforce.aUtils.core.config.ASQLiteFile;
import com.elytraforce.aUtils.core.logger.ALogger;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*TODO Experimental Class. I do not work with SQLite on a regular basis and therefore this class
is subject to change. Most of the code here currently is probably improper or wrong and therefore
should not be used in a production server as though i am versed with working with connection pooling
and mysql, i know jack shit about SQLite.*/

/**
 * SQLite implementation of a datakore
 */
public class SQLiteDataKore extends DataKore {

    private final File databaseLocation;

    public SQLiteDataKore(String databaseName, ALogger logger) {
        super(databaseName, logger);

        databaseLocation = new ASQLiteFile().create(databaseName).getDatabaseLocation();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("SQLite Driver not found! Please contact the developer of this plugin!");
        }

        logger.debug("Successfully created a SQLiteDataKore with the following info: \n"
                + "Jdbc URL: " + databaseName + "\n");


    }

    @Override
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + databaseLocation);
    }

    @Override
    public void close() {
        logger.info("SQLiteDataKore - Shutting Down! Goodbye!");
    }
}
