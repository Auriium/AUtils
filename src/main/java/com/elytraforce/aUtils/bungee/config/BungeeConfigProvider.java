package com.elytraforce.aUtils.bungee.config;

import com.elytraforce.aUtils.core.config.AConfigProvider;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BungeeConfigProvider extends AConfigProvider<Configuration> {

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
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
            this.config = new Configuration();
        }
    }

    @Override
    public void save(File file) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Configuration getConfig() {
        return config;
    }

}
