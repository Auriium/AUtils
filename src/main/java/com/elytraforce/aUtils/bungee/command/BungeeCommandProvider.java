package com.elytraforce.aUtils.bungee.command;

import com.elytraforce.aUtils.bungee.BungeePlugin;
import com.elytraforce.aUtils.core.command.ACommand;
import com.elytraforce.aUtils.core.command.ACommandManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BungeeCommandProvider extends ACommandManager {

    @Inject
    private BungeePlugin plugin;

    @Inject
    public BungeeCommandProvider(BungeePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void registerCommand(ACommand command) {
        verifyCommand(command);

        if (this.commands.stream().anyMatch(command1 -> command1.getName().equalsIgnoreCase(command.getName()))) {
            throw new IllegalArgumentException("The commandManager already contains command named " + command.getName());
        }
        if (this.commands.contains(command)) {
            throw new IllegalArgumentException("The commandManager already contains command class " + command.getClass().getName());
        }

        plugin.getProxy().getPluginManager().registerCommand(plugin,new BungeeCommandWrapper(command,this));
        this.commands.add(command);

    }
}
