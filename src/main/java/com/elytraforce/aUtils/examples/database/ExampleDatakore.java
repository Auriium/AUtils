package com.elytraforce.aUtils.examples.database;

import com.elytraforce.aUtils.core.database.ADatakore;
import org.slf4j.Logger;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class ExampleDatakore extends ADatakore {

    @Override
    public String datacorePluginName() {
        return "examplePlugin";
    }

    @Override
    public String datacoreDatabase() {
        return "players";
    }

    @Override
    public String datacorePassword() {
        return "password123";
    }

    @Override
    public String datacoreUsername() {
        return "owners";
    }

    @Override
    public String datacoreHost() {
        return "192.168.1.1";
    }

    @Override
    public int datacorePort() {
        return 3306;
    }

    @Override
    public boolean datacoreUseSSL() {
        return false;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public void getOptions() {
        //nothing here
    }

    public ExampleDatakore() {
        this.open();
    }

    public void shutdown() {
        //do menial tasks here
        this.close();
    }

    public CompletableFuture<Integer> getPlayerInteger(UUID uuid) {
        String sql = "";

        return CompletableFuture.supplyAsync(() -> {
            try (CachedRowSet set = query(sql, uuid)) {
                return set.getInt("sex");
            } catch (SQLException e) {
                throw new CompletionException(e);
            }
        });
    }
}
