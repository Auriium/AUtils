package com.elytraforce.aUtils.spigot.logger;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class SpigotLogger implements ALogger {

    @Inject
    JavaPlugin plugin;

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
}
