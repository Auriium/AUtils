package com.elytraforce.aUtils.spigot.command;

import com.elytraforce.aUtils.core.command.ACommand;
import com.elytraforce.aUtils.core.command.ACommandManager;
import com.elytraforce.aUtils.spigot.SpigotPlugin;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

@Singleton
public class SpigotCommandProvider extends ACommandManager {

    @Inject private SpigotPlugin plugin;

    private SimpleCommandMap commandMap;

    @Inject
    public SpigotCommandProvider(SpigotPlugin plugin) {
        SimplePluginManager spm = (SimplePluginManager) Bukkit.getPluginManager();
        try {

            Field field = FieldUtils.getDeclaredField( spm.getClass(), "commandMap", true );
            commandMap = (SimpleCommandMap) field.get( spm );

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerCommand(ACommand command) {
        verifyCommand(command);

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
