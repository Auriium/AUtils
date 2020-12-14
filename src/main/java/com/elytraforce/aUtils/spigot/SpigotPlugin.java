package com.elytraforce.aUtils.spigot;

import com.elytraforce.aUtils.core.plugin.APlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotPlugin extends JavaPlugin implements APlugin<JavaPlugin> {

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }
}
