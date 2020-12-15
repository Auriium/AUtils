package com.elytraforce.aUtils.bungee.config;

import com.elytraforce.aUtils.bungee.BungeePlugin;
import com.elytraforce.aUtils.core.config.AConfigProvider;
import com.elytraforce.aUtils.core.config.AFile;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/*public abstract class BungeeConfig extends AConfigProvider<Configuration> {

    @Inject ALogger logger;
    @Inject BungeePlugin plugin;

    @Override
    public BungeeConfig create() {

        file = new File(plugin.getDataFolder(), this.filePosition());
        config = new Configuration();

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
    public BungeeConfig create(File file) {
        config = new Configuration();

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
    public BungeeConfig load() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (Exception e) {
            logger.error("IOException loading config!");
        }

        Class<? extends BungeeConfig> cls = getClass();

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
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file);
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
    public BungeeConfig save() {
        Class<? extends BungeeConfig> cls = getClass();

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
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file);
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
