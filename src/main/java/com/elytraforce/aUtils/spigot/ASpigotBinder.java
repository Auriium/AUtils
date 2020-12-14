package com.elytraforce.aUtils.spigot;

import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.core.util.ABinder;
import com.elytraforce.aUtils.spigot.logger.SpigotLogger;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ASpigotBinder extends ABinder {

    private final SpigotPlugin plugin;

    public ASpigotBinder(SpigotPlugin plugin) {
        super(plugin);

        this.plugin = plugin;
    }

    @Override
    public void configureVersionSpecific() {
        this.bind(JavaPlugin.class).toInstance(plugin);
        this.bind(ALogger.class).to(SpigotLogger.class);
    }

}
