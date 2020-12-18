package com.elytraforce.aUtils.spigot.command;

import com.elytraforce.aUtils.core.command.model.ACommand;
import com.elytraforce.aUtils.core.command.ACommandManager;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

@Singleton
public class SpigotCommandProvider extends ACommandManager {

    @Inject private SpigotPlugin plugin;

    private CommandMap commandMap;

    @Inject
    public SpigotCommandProvider(SpigotPlugin plugin) {
        try {

            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);

            this.commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerCommand(ACommand command) {
        if (this.commands.stream().anyMatch(command1 -> command1.getName().equalsIgnoreCase(command.getName()))) {
            throw new IllegalArgumentException("The commandManager already contains command named " + command.getName());
        }
        if (this.commands.contains(command)) {
            throw new IllegalArgumentException("The commandManager already contains command class " + command.getClass().getName());
        }



        SpigotCommandWrapper spigotCommand = new SpigotCommandWrapper(command,this);
        spigotCommand.setPermission(command.getPermission());
        spigotCommand.setAliases(command.getAliases());
        spigotCommand.setDescription(command.getDescription());

        commandMap.register(plugin.getName(),spigotCommand);
        this.commands.add(command);

    }
}
