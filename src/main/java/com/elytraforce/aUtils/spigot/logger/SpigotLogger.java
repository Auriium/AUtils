package com.elytraforce.aUtils.spigot.logger;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.google.inject.Inject;

import java.util.logging.Level;

public class SpigotLogger implements ALogger {

    @Inject SpigotPlugin plugin;

    @Override
    public void log(String string) {
        plugin.getLogger().log(Level.SEVERE,string);
    }

    @Override
    public void info(String string) {
        plugin.getLogger().log(Level.SEVERE,string);
    }

    @Override
    public void warning(String string) {
        plugin.getLogger().log(Level.SEVERE,string);
    }

    @Override
    public void error(String string) {
        plugin.getLogger().log(Level.SEVERE,string);
    }

    @Override
    public void debug(String string) {

    }
}
