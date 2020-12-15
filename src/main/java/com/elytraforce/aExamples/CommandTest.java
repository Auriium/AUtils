package com.elytraforce.aExamples;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandTest implements CommandExecutor {

    @Inject public ConfigTest config;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("I AM BIG BERT");

        sender.sendMessage(config.godIsHere);

        String sex = args[0];

        config.godIsHere = sex;

        config.save();

        sender.sendMessage(config.godIsHere);
        return true;
    }
}
