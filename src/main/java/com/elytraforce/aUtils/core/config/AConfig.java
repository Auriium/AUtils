package com.elytraforce.aUtils.core.config;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.core.plugin.APlugin;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Represents a file that is used to hold configuration files. Automatically fills fields if they are missing
 * as well as handles all saving loading and creating.
 */
@SuppressWarnings("unchecked")
public abstract class AConfig extends ALoadableFile{

    @Inject private AConfigProvider provider;
    @Inject private APlugin plugin;
    @Inject private ALogger logger;

    public Object getProvider(String path, Object def) {
        return provider.get(path,def);
    }

    public void saveProvider() {
        provider.save(file);
    }

    public void loadProvider() {
        provider.load(file);
    }

    public void setProvider(String path, Object toSet) {
        provider.set(path,toSet);
    }

    @Override
    public AConfig load() {

        provider.load(file);

        Class<? extends AConfig> cls = getClass();

        for(Field f : cls.getFields())
        {
            if(f.isAnnotationPresent(AField.class))
            {
                String target;
                AField cf = f.getAnnotation(AField.class);
                target = cf.location();
                if(target.isEmpty())
                    target = f.getName();
                try
                {
                    if(!cf.comment().isEmpty())
                    {
                        if (!provider.contains(target)) { provider.set(target + "_COMMENT", cf.comment()); }
                    }
                    if (!provider.contains(target)) {provider.set(target, f.get(this));}
                }
                catch (IllegalArgumentException | IllegalAccessException e) { return null; }
            }
        }

        provider.save(file);

        for(Field f : cls.getFields())
        {
            if(f.isAnnotationPresent(AField.class))
            {
                String target;
                AField cf = f.getAnnotation(AField.class);
                target = cf.location();
                if(target.isEmpty())
                    target = f.getName();

                try
                {
                    f.set(this, provider.get(target, f.get(this)));
                }
                catch (IllegalArgumentException | IllegalAccessException e) {logger.error("IllegalArgument exception loading config " + file.getName());}
            }
        }
        return this;
    }

    @Override
    public AConfig save() {
        Class<? extends AConfig> cls = getClass();

        for(Field f : cls.getFields())
        {
            if(f.isAnnotationPresent(AField.class))
            {
                String target;
                AField cf = f.getAnnotation(AField.class);
                target = cf.location();
                if(target.isEmpty())
                    target = f.getName();
                try
                {
                    if(!cf.comment().isEmpty())
                    {
                        provider.set(target + "_COMMENT", cf.comment());
                    }

                    provider.set(target, f.get(this));
                }
                catch (IllegalArgumentException | IllegalAccessException e) { return null; }
            }
        }

        provider.save(file);

        return this;
    }

    @Override
    public AConfig create() {
        this.create(new File(plugin.getDataFolder(), this.filePosition()));

        return this;
    }

    @Override
    public AConfig create(File file) {
        this.file = file;

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                logger.error("IOException creating config " + file.getName());
            }

            load();
        }
        return this;
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AField
    {
        String location() default "";
        String comment() default "";
    }

}
