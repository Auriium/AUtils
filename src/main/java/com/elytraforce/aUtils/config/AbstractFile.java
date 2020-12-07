package com.elytraforce.aUtils.config;

import java.io.File;

public abstract class AbstractFile {
    public abstract <T extends AbstractFile> AbstractFile load();
    public abstract <T extends AbstractFile> AbstractFile save();
    public abstract <T extends AbstractFile> AbstractFile create();
    public abstract <T extends AbstractFile> AbstractFile create(File file);

    public abstract String filePosition();
}
