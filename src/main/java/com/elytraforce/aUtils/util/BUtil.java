package com.elytraforce.aUtils.util;

import net.md_5.bungee.api.plugin.Plugin;
import org.slf4j.LoggerFactory;

public class BUtil extends AbstractUtil<Plugin> {

    private static BUtil utilInstance;

    private BUtil(Plugin plugin) {
        super(plugin);
        utilInstance = this;
        pluginName = pluginInstance.getDescription().getName();
        logger = LoggerFactory.getLogger(pluginInstance.getDescription().getName());
    }

    public static BUtil register(Plugin plugin) {
        return new BUtil(plugin);
    }

    public boolean isDebug() { return isDebug; }
    public Plugin getPlugin() {
        return pluginInstance;
    }

    public static BUtil getUtils() {
        return utilInstance;
    }

}
