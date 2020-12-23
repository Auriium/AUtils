package com.elytraforce.aUtils.bungee.command;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import net.md_5.bungee.api.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BungeeSenderWrapper implements ASenderWrapper<CommandSender> {

    private CommandSender sender;

    public BungeeSenderWrapper(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public String getName() {
        return sender.getName();
    }

    @Override
    public UUID getUUID() {
        if (isConsole()) {
            throw new IllegalStateException("Sender is a console!");
        } else {
            Player player = (Player) sender;
            return player.getUniqueId();
        }
    }

    @Override
    public boolean hasPermission(String perm) {
        return sender.hasPermission(perm);
    }

    @Override
    public boolean isConsole() { return !(sender instanceof Player); }

    @Override
    public void sendMessage(String string) { sender.sendMessage(string); }

    @Override
    public CommandSender getWrapped() {
        return this.sender;
    }

    @Override
    public boolean equals(ASenderWrapper sender) {
        if (sender.isConsole() && this.isConsole()) return true;
        return sender.getUUID().equals(this.getUUID());
    }

}

