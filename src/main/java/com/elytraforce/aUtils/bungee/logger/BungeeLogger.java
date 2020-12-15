package com.elytraforce.aUtils.bungee.logger;

import com.elytraforce.aUtils.bungee.BungeePlugin;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;

import java.util.logging.Level;

public class BungeeLogger implements ALogger {

    @Inject
    BungeePlugin plugin;

    @Override
    public void log(String string) {
        plugin.getLogger().log(Level.INFO,string);
    }

    @Override
    public void info(String string) {
        plugin.getLogger().log(Level.INFO,string);
    }

    @Override
    public void warning(String string) {
        plugin.getLogger().log(Level.WARNING,string);
    }

    @Override
    public void error(String string) {
        plugin.getLogger().log(Level.SEVERE,string);
    }

}
