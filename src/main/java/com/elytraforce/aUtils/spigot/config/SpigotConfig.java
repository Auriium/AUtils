package com.elytraforce.aUtils.spigot.config;

import com.elytraforce.aUtils.core.config.AConfigProvider;
import com.elytraforce.aUtils.core.config.AFile;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.google.inject.Inject;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/*
public abstract class SpigotConfig extends AConfigProvider<YamlConfiguration> {

    private YamlConfiguration config;

    @Inject private SpigotPlugin plugin;
    @Inject private ALogger logger;

    @Override
    public SpigotConfig create() {
        file = new File(plugin.getDataFolder(), this.filePosition());
        config = new YamlConfiguration();

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
    public SpigotConfig create(File file) {
        this.file = file;
        config = new YamlConfiguration();

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
    public SpigotConfig load() {
        try {
            config.load(file);
        } catch (Exception e) {
            logger.error("IOException loading config!");
        }

        Class<? extends SpigotConfig> cls = getClass();

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
                        if (!config.contains(target)) { config.set(target + "_COMMENT", cf.comment()); }
                    }
                    if (!config.contains(target)) {config.set(target, f.get(this));}
                }
                catch (IllegalArgumentException | IllegalAccessException e) { return null; }
            }
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    f.set(this, config.get(target, f.get(this)));
                }
                catch (IllegalArgumentException | IllegalAccessException e) {logger.error("IllegalArgument exception loading config " + file.getName());}
            }
        }
        return this;
    }

    @Override
    public SpigotConfig save() {
        Class<? extends SpigotConfig> cls = getClass();

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
                        config.set(target + "_COMMENT", cf.comment());
                    }

                    config.set(target, f.get(this));
                }
                catch (IllegalArgumentException | IllegalAccessException e) { return null; }
            }
        }

        try {
            config.save(file);
        } catch (Exception e) {
            logger.error("IOException saving config!");
        }

        return this;
    }

    //Reload after setting!
    public void set(String position, Object value) {
        config.set(position, value);
    }

    public static String serialize(Object object) {
        return null;
    }


}*/
