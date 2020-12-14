package com.elytraforce.aUtils.util;

import net.md_5.bungee.api.plugin.Plugin;
import org.slf4j.LoggerFactory;

public class BUtil extends AbstractUtil<Plugin> {

    private static BUtil utilInstance;

    private BUtil(Plugin plugin) {
        super(plugin);

        isDebug = false;
        utilInstance = this;
        pluginName = pluginInstance.getDescription().getName();
        logger = LoggerFactory.getLogger(pluginInstance.getDescription().getName());
    }

    public static BUtil register(Plugin plugin) {
        return new BUtil(plugin);
    }

    public boolean isDebug() { return isDebug; }
    public Plugin getPlugin() {
        if (pluginInstance == null) {
            throw new IllegalStateException("The plugin has not been registered yet!");
        }
        return pluginInstance;
    }

    public static BUtil getUtils() {
        if (utilInstance == null) {
            throw new IllegalStateException("The utilities have not been registered yet!");
        }
        return utilInstance;
    }

}
