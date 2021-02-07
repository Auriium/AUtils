package com.elytraforce.aUtils.core.config;

import com.elytraforce.aUtils.core.database.SQLiteKore;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.core.plugin.APlugin;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;

/*TODO Experimental Class. I do not work with SQLite on a regular basis and therefore this class
is subject to change. Most of the code here currently is probably improper or wrong and therefore
should not be used in a production server as though i am versed with working with connection pooling
and mysql, i know jack shit about SQLite.*/

/**
 * A class that can be created, and is specifically purposed for
 * {@link SQLiteKore} to use as a SQLite database.
 */
public class ASQLiteFile extends AFile {

    @Inject private ALogger logger;
    @Inject private APlugin plugin;


    public File getDatabaseLocation() {
        return this.file;
    }

    @Override
    public ASQLiteFile create() {
        return create("database");
    }

    public ASQLiteFile create(String string) {
        return create(new File(plugin.getDataFolder(), string + ".db"));
    }

    @Override
    public ASQLiteFile create(File file) {
        this.file = file;

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                logger.error("IOException creating config " + file.getName());
            }
        }

        return this;
    }

    @Override
    public String filePosition() {
        return "database.db";
    }
}
