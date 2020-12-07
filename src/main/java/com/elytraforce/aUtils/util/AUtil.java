package com.elytraforce.aUtils.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.LoggerFactory;

public class AUtil extends AbstractUtil<JavaPlugin> {

    private static AUtil utilInstance;

    private AUtil(JavaPlugin plugin) {
        super(plugin);

        utilInstance = this;
        pluginName = pluginInstance.getName();
        logger = LoggerFactory.getLogger(pluginInstance.getName());
    }

    public static AUtil register(JavaPlugin plugin) {
        return new AUtil(plugin);
    }

    public boolean isDebug() { return isDebug; }
    public JavaPlugin getPlugin() {
        return pluginInstance;
    }

    public static AUtil getUtils() {
        return utilInstance;
    }

}
