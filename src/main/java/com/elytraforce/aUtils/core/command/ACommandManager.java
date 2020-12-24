package com.elytraforce.aUtils.core.command;

import java.util.LinkedHashSet;

/**
 * Abstraction representing something that can hold commands and register them. Implemented by
 * {@link com.elytraforce.aUtils.bungee.command.BungeeCommandProvider} and
 * {@link com.elytraforce.aUtils.spigot.command.SpigotCommandProvider}
 */
public abstract class ACommandManager {

    protected LinkedHashSet<ACommand> commands = new LinkedHashSet<>();
    protected LinkedHashSet<ASenderWrapper> senders = new LinkedHashSet<>();

    public abstract void registerCommand(ACommand command);
    public LinkedHashSet<ACommand> getCommands() { return this.commands; };

}
