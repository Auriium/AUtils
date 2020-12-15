package com.elytraforce.aUtils.spigot;

import com.elytraforce.aUtils.core.plugin.APlugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SpigotPlugin extends JavaPlugin implements APlugin<JavaPlugin> {

    protected boolean debug;

    public boolean isDebug() { return this.debug; }

    @Override
    public JavaPlugin getPlugin() {
        return this;
    }
}
