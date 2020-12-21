package com.elytraforce.aUtils.spigot.command;

import com.elytraforce.aUtils.core.command.ACommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

//Wrapper for commandSender
public class SpigotSenderWrapper implements ACommandSender{

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
    public boolean isConsole() {
        return !(sender instanceof Player);
    }

    @Override
    public void sendMessage(String string) {
        sender.sendMessage(string);
        return;
    }

    public Player castToPlayer() {
        if (isConsole()) throw new IllegalStateException("The sender you tried to cast was a wrapper!");
        return (Player) sender;
    }
}
