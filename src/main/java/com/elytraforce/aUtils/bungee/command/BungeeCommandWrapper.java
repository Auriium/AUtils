package com.elytraforce.aUtils.bungee.command;

import com.elytraforce.aUtils.core.command.ACommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BungeeCommandWrapper extends Command implements TabExecutor {
    private final ACommand internal;
    private final BungeeCommandProvider provider;

    public BungeeCommandWrapper(ACommand command, BungeeCommandProvider provider) {
        super(command.getName(), command.getPermission(), command.getAliases().toArray(new String[0]));

        this.internal = command;
        this.provider = provider;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        internal.execute(new BungeeSenderWrapper(sender),args);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return internal.executeTab(new BungeeSenderWrapper(sender),args);
    }


}
