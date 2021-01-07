package com.elytraforce.aUtils.core.command;

import java.util.LinkedHashSet;
import java.util.Objects;

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

    public void verifyCommand(ACommand command) {
        Objects.requireNonNull(command.getAliases(),"Alias cannot be null!");
        Objects.requireNonNull(command.getDescription(),"Description cannot be null!");
        Objects.requireNonNull(command.getName(),"Command cannot be null!");
        Objects.requireNonNull(command.getUsage(),"Usage cannot be null!");
        Objects.requireNonNull(command.getPermission(),"Permission cannot be null!");
    }


}
