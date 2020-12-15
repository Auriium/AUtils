package com.elytraforce.aUtils.bungee.logger;

import com.elytraforce.aUtils.bungee.BungeePlugin;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;

public class BungeeLogger implements ALogger {

    @Inject
    Plugin plugin;

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

    @Override
    public void debug(String string) {
        /*if (plugin.isDebug()) {
            plugin.getLogger().log(Level.WARNING, string);
        }*/
    }

}
