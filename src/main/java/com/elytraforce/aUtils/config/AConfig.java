package com.elytraforce.aUtils.config;

import com.elytraforce.aUtils.ALogger;
import com.elytraforce.aUtils.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@SuppressWarnings("unused")
public abstract class AConfig extends AbstractFile{

    private static File file;
    private static YamlConfiguration config;

    public AConfig create() {
        file = new File(Main.getPlugin().getDataFolder(), this.filePosition());
        config = new YamlConfiguration();

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ALogger.logError("IOException creating config " + file.getName());
            }

            load();
        }
        return this;
    }

    public AConfig create(File file) {
        AConfig.file = file;
        config = new YamlConfiguration();

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ALogger.logError("IOException creating config " + file.getName());
            }
            load();
        }
        return this;
    }

    public AConfig load() {
        try {
            config.load(file);
        } catch (Exception e) {
            ALogger.logError("IOException loading config!");
        }

        Class<? extends AConfig> cls = getClass();

        for(Field f : cls.getFields())
        {
            if(f.isAnnotationPresent(ConfigField.class))
            {
                String target = "";
                ConfigField cf = f.getAnnotation(ConfigField.class);
                target = cf.location();
                if(target.isEmpty())
                    target = f.getName();

                try
                {
                    f.set(this, config.get(target, f.get(this)));
                }
                catch (IllegalArgumentException | IllegalAccessException e) { ALogger.logError("IllegalArgument exception loading config " + file.getName());}
            }
        }
        return this;
    }

    public AConfig save() {
        try {
            config.save(file);
        } catch (Exception e) {
            ALogger.logError("IOException saving config!");
        }
        Class<? extends AConfig> cls = getClass();

        for(Field f : cls.getFields())
        {
            if(f.isAnnotationPresent(ConfigField.class))
            {
                String target = "";
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
