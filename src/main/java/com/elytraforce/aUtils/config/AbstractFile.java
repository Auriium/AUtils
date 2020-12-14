package com.elytraforce.aUtils.config;

import java.io.File;

public abstract class AbstractFile<T> {

    protected File file;

    public abstract T load();
    public abstract T save();
    public abstract T create();
    public abstract T create(File file);

    public abstract String filePosition();
}
