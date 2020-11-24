package com.elytraforce.aUtils;

import org.bukkit.plugin.java.JavaPlugin;

public class AUtil {

    private static JavaPlugin instance;
    private static AUtil utilInstance;

    public AUtil(JavaPlugin plugin) {
        instance = plugin;
        utilInstance = this;
    }

    public static JavaPlugin getPlugin() {
        return instance;
    }

    public static AUtil getUtils() {
        return utilInstance;
    }

}
