package com.elytraforce.aUtils.config;

import com.elytraforce.aUtils.util.AUtil;
import com.elytraforce.aUtils.util.BUtil;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@SuppressWarnings("unused")
public abstract class BConfig extends AbstractFile<BConfig>{

    private Configuration config;

    @Override
    public BConfig create() {

        file = new File(BUtil.getUtils().getPlugin().getDataFolder(), this.filePosition());
        config = new Configuration();

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                AUtil.getUtils().error("IOException creating config " + file.getName());
            }

            load();
        }

        return this;
    }

    @Override
    public BConfig create(File file) {
        config = new Configuration();

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                AUtil.getUtils().error("IOException creating config " + file.getName());
            }

            load();
        }

        return this;
    }

    @Override
    public BConfig load() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (Exception e) {
            AUtil.getUtils().error("IOException loading config!");
        }

        Class<? extends BConfig> cls = getClass();

        for(Field f : cls.getFields())
        {
            if(f.isAnnotationPresent(ConfigField.class))
            {
                String target;
                ConfigField cf = f.getAnnotation(ConfigField.class);
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
            if(f.isAnnotationPresent(ConfigField.class))
            {
                String target;
                ConfigField cf = f.getAnnotation(ConfigField.class);
                target = cf.location();
                if(target.isEmpty())
                    target = f.getName();

                try
                {
                    f.set(this, config.get(target, f.get(this)));
                }
                catch (IllegalArgumentException | IllegalAccessException e) {AUtil.getUtils().error("IllegalArgument exception loading config " + file.getName());}
            }
        }
        return this;
    }

    @Override
    public BConfig save() {
        Class<? extends BConfig> cls = getClass();

        for(Field f : cls.getFields())
        {
            if(f.isAnnotationPresent(ConfigField.class))
            {
                String target;
                ConfigField cf = f.getAnnotation(ConfigField.class);
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
            AUtil.getUtils().error("IOException saving config!");
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

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ConfigField
    {
        String location() default "";
        String comment() default "";
    }
}

