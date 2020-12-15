package com.elytraforce.aUtils.core.plugin;

import java.io.File;

public interface APlugin<T> {
    public T getPlugin();

    public File getDataFolder();

    //public void runAsync(Runnable runnable);

    //public void run(Runnable runnable);


}
