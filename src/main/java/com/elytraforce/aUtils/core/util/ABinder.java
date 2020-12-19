package com.elytraforce.aUtils.core.util;

import com.elytraforce.aUtils.bungee.BungeePlugin;
import com.elytraforce.aUtils.bungee.chat.BungeeChat;
import com.elytraforce.aUtils.bungee.config.BungeeConfigProvider;
import com.elytraforce.aUtils.bungee.logger.BungeeLogger;
import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.command.ACommandManager;
import com.elytraforce.aUtils.core.config.AConfigProvider;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.elytraforce.aUtils.core.plugin.APlugin;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.elytraforce.aUtils.spigot.chat.SpigotChat;
import com.elytraforce.aUtils.spigot.command.SpigotCommandProvider;
import com.elytraforce.aUtils.spigot.config.SpigotConfigProvider;
import com.elytraforce.aUtils.spigot.logger.SpigotLogger;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Abstract binder extending a Guice module that handles boilerplate code
 * and binding the correct providers to their abstractions based on the type
 * of plugin.
 */
public abstract class ABinder extends AbstractModule {
    private final APlugin plugin;

    public ABinder(APlugin plugin) {
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
            this.bind(ACommandManager.class).to(SpigotCommandProvider.class);
        } else if (plugin instanceof BungeePlugin) {
            this.bind(Plugin.class).toInstance((Plugin) plugin);
            this.bind(BungeePlugin.class).toInstance((BungeePlugin) plugin);
            this.bind(ALogger.class).to(BungeeLogger.class);
            this.bind(AChat.class).to(BungeeChat.class);
            this.bind(AConfigProvider.class).to(BungeeConfigProvider.class);
        } else {
            throw new IllegalArgumentException("Your plugin is not an spigot or bungee plugin! Please extend ABinder instead!");
        }

    }

}
