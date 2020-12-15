package com.elytraforce.aUtils.core.config;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class AFile {

    protected File file;

    public abstract AFile load();
    public abstract AFile save();
    public abstract AFile create();
    public abstract AFile create(File file);

    public abstract String filePosition();

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AField
    {
        String location() default "";
        String comment() default "";
    }
}

