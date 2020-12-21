package com.elytraforce.aExamples;

import com.google.inject.Inject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CommandTest extends Command implements TabExecutor {

    public ConfigTest config;

    @Inject
    public CommandTest(@NotNull String name, ConfigTest config) {
        super(name);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return this.execute(sender,label,args);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        sender.sendMessage("I AM BIG BERT");

        sender.sendMessage(config.godIsHere);

        String sex = args[0];

        config.godIsHere = sex;

        config.save();

        sender.sendMessage(config.godIsHere);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Arrays.asList("god","cum");
    }
}
