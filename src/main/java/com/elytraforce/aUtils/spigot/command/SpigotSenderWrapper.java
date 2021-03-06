package com.elytraforce.aUtils.spigot.command;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

//Wrapper for commandSender
public class SpigotSenderWrapper implements ASenderWrapper<CommandSender> {

    private CommandSender sender;

    public SpigotSenderWrapper(CommandSender sender) {
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
