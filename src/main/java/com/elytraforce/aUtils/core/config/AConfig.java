package com.elytraforce.aUtils.core.config;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.core.plugin.APlugin;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public abstract class AConfig extends AFile{

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
    public AFile load() {

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
    public AFile save() {
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
    public AFile create() {
        file = new File(plugin.getDataFolder(), this.filePosition());

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

    @Override
    public AFile create(File file) {
        file = new File(plugin.getDataFolder(), "config.yml");

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

}
