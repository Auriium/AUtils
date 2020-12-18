package com.elytraforce.aUtils.core.plugin;

import com.google.inject.Binder;

import java.io.File;

public interface APlugin<T> {
    //TODO: let APlugin do more cool shit

    public String getPluginName();
    public T getPlugin();
    public File getDataFolder();

    //public void runAsync(Runnable runnable);

    //public void run(Runnable runnable);


}
