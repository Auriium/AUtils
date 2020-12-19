package com.elytraforce.aUtils.core.config;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a file that can be created, loaded, and saved, and has a set position.
 */
public abstract class AFile {

    protected File file;

    public abstract AFile load();
    public abstract AFile save();
    public abstract AFile create();
    public abstract AFile create(File file);

    public abstract String filePosition();
}

