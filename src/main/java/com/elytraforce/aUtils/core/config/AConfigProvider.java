package com.elytraforce.aUtils.core.config;

import java.io.File;

public abstract class AConfigProvider<T> {

    protected T config;

    public abstract Object get(String path, Object def);
    public abstract void set(String path, Object toSet);
    public abstract void load(File file);
    public abstract void save(File file);
    public abstract boolean contains(String path);
    public abstract T getConfig();

}
