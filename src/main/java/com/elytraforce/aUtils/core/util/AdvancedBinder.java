package com.elytraforce.aUtils.core.util;

import com.elytraforce.aUtils.bungee.BungeePlugin;
import com.elytraforce.aUtils.bungee.chat.BungeeChat;
import com.elytraforce.aUtils.bungee.config.BungeeConfigProvider;
import com.elytraforce.aUtils.bungee.logger.BungeeLogger;
import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.config.AConfigProvider;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.core.plugin.APlugin;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.elytraforce.aUtils.spigot.chat.SpigotChat;
import com.elytraforce.aUtils.spigot.config.SpigotConfigProvider;
import com.elytraforce.aUtils.spigot.logger.SpigotLogger;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AdvancedBinder extends AbstractModule {
    private final APlugin plugin;

    public AdvancedBinder(APlugin plugin) {
        this.plugin = plugin;
    }

    public Injector createInjector() { return Guice.createInjector(this);
    }

    public abstract void configureSpecific();

    @Override
    public final void configure() {
        this.bind(APlugin.class).toInstance(this.plugin);

        configureSpecific();

        if (plugin instanceof SpigotPlugin) {
            this.bind(JavaPlugin.class).toInstance((JavaPlugin) plugin);
            this.bind(SpigotPlugin.class).toInstance((SpigotPlugin) plugin);
            this.bind(ALogger.class).to(SpigotLogger.class);
            this.bind(AChat.class).to(SpigotChat.class);
            this.bind(AConfigProvider.class).to(SpigotConfigProvider.class);
        } else if (plugin instanceof BungeePlugin) {
            this.bind(Plugin.class).toInstance((Plugin) plugin);
            this.bind(BungeePlugin.class).toInstance((BungeePlugin) plugin);
            this.bind(ALogger.class).to(BungeeLogger.class);
            this.bind(AChat.class).to(BungeeChat.class);
            this.bind(AConfigProvider.class).to(BungeeConfigProvider.class);
        } else {
            throw new IllegalArgumentException("Your plugin is not an aUtils plugin!");
        }

    }
}
