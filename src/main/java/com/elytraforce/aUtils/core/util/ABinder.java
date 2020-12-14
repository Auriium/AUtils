package com.elytraforce.aUtils.core.util;

import com.elytraforce.aUtils.bungee.BungeePlugin;
import com.elytraforce.aUtils.core.plugin.APlugin;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ABinder extends AbstractModule {
    private final APlugin plugin;

    public ABinder(APlugin plugin) {
        this.plugin = plugin;
    }

    public abstract Injector createInjector();

    public abstract void configureSpecific();

    public abstract void configureVersionSpecific();

    @Override
    public final void configure() {
        this.bind(APlugin.class).toInstance(this.plugin);

        configureVersionSpecific();
        configureSpecific();

    }
}
