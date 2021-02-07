package com.elytraforce.aUtils.core.config;

/**
 * Represents a class that can be loaded and saved as well as has a file
 */
public abstract class ALoadableFile extends AFile {

    public abstract <T extends ALoadableFile> T load();
    public abstract <T extends ALoadableFile> T save();

}
