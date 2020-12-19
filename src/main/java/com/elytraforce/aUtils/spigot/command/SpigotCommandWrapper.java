package com.elytraforce.aUtils.spigot.command;

import com.elytraforce.aUtils.core.command.ACommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

//Wrapper for Command/TabExecutor
public class SpigotCommandWrapper extends Command{

    private final ACommand internal;
    private final SpigotCommandProvider provider;

    protected SpigotCommandWrapper(ACommand command, SpigotCommandProvider provider) {
        super(command.getName(), command.getDescription(), command.getUsage(), command.getAliases());

        this.internal = command;
        this.provider = provider;
    }


    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return internal.execute(new SpigotSenderWrapper(sender),args);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return internal.executeTab(new SpigotSenderWrapper(sender),args);
    }

}
