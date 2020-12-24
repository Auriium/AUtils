package com.elytraforce.aUtils.core.config;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a file that can be created and has a set position.
 */
public abstract class AFile {

    protected File file;

    public abstract <T extends AFile> T create();
    public abstract <T extends AFile> T create(File file);

    public abstract String filePosition();
}

