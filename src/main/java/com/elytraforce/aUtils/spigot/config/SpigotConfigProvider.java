package com.elytraforce.aUtils.spigot.config;

import com.elytraforce.aUtils.core.config.AConfigProvider;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;


import java.io.File;
import java.io.IOException;

public class SpigotConfigProvider extends AConfigProvider<YamlConfiguration> {

    @Override
    public boolean contains(String path) {
        return config.contains(path);
    }

    @Override
    public Object get(String path, Object def) {
        return config.get(path,def);
    }

    @Override
    public void set(String path, Object toSet) {
        config.set(path,toSet);
    }

    @Override
    public void load(File file) {
        try {
            if (config == null) {
                this.config = new YamlConfiguration();
            }
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            this.config = new YamlConfiguration();
        }
    }

    @Override
    public void save(File file) {
        try {
            if (config == null) {
                this.config = new YamlConfiguration();
            }
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public YamlConfiguration getConfig() {
        return config;
    }

}
