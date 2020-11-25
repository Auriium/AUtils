package com.elytraforce.aUtils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AUtil {

    private static JavaPlugin instance;
    private static AUtil utilInstance;
    private boolean isDebug;

    private AUtil(JavaPlugin instance) {
        utilInstance = this; AUtil.instance = instance;
    }

    public AUtil withDebugLogging() {
        isDebug = true;
        return this;
    }

    public boolean pluginExists(String string) {
        return (Bukkit.getPluginManager().getPlugin(string)) != null;
    }

    public boolean isDebug() { return this.isDebug; }

    public static JavaPlugin getPlugin() {
        return instance;
    }

    public static AUtil getUtils() {
        return utilInstance;
    }

}
