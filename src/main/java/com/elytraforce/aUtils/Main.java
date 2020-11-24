package com.elytraforce.aUtils;

import org.bukkit.plugin.java.JavaPlugin;

public class Main {

    private static JavaPlugin instance;
    private static Main utilInstance;

    public Main(JavaPlugin plugin) {
        instance = plugin;
        utilInstance = this;
    }

    public static JavaPlugin getPlugin() {
        return instance;
    }

    public static Main getUtils() {
        return utilInstance;
    }

}
