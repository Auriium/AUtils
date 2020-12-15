package com.elytraforce.aUtils.bungee;

import com.elytraforce.aUtils.bungee.logger.BungeeLogger;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.core.util.ABinder;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class ABungeeBinder extends ABinder {

    private final BungeePlugin plugin;

    public ABungeeBinder(BungeePlugin plugin) {
        super(plugin);

        this.plugin = plugin;
    }

    @Override
    public void configureVersionSpecific() {
        this.bind(Plugin.class).toInstance(plugin);
        this.bind(ALogger.class).to(BungeeLogger.class);
    }

}

