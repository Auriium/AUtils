package com.elytraforce.aUtils.core.plugin;

import java.io.File;

/**
 * Interface representing and wrapping a plugin
 *
 * Implemented by {@see SpigotPlugin} and {@see BungeePlugin}
 * @param <T> represents the plugin being wrapped
 */
public interface APlugin<T> {
    //TODO: let APlugin do more cool shit

    public String getPluginName();
    public T getPlugin();
    public File getDataFolder();

    //public void runAsync(Runnable runnable);

    //public void run(Runnable runnable);


}
