package com.elytraforce.aUtils.config;

import java.io.File;

public abstract class AbstractFile {
    public abstract AbstractFile load();
    public abstract AbstractFile save();
    public abstract AbstractFile create();
    public abstract AbstractFile create(File file);

    public abstract String filePosition();
}
