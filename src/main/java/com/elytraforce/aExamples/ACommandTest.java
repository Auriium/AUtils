package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.model.AFancyExecutor;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ACommandTest extends AFancyExecutor {

    @Inject ConfigTest config;
    @Inject ALogger logger;

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public int getMaxArgs() {
        return 2;
    }

    @Override
    public String getPrefix() {
        return "&9&lPlugin&f&lTest &7>> &r";
    }

    @Override
    public boolean isConsoleAccessible() {
        return false;
    }

    @Override
    public String getName() {
        return "ballcommand";
    }

    @Override
    public ArrayList<String> getAliases() {
        ArrayList<String> shit = new ArrayList<>();
        shit.add("HELLO");
        return shit;
    }

    @Override
    public String getPermission() {
        return "plugintest.coolPermission";
    }

    @Override
    public String getUsage() {
        return "slap my balls";
    }

    @Override
    public String getDescription() {
        return "cool command that you can use";
    }

    @Override
    public boolean onCommand(ACommandSender sender, String[] args) {
        sender.sendMessage("I AM EATING FOOD NOW");
        return true;
    }

    @Override
    public List<String> onTabComplete(ACommandSender sender, String[] args) {
        logger.warning(args.length + "legnth");

        switch (args.length) {
            case 1:
                return copyPartialMatches(args[0],Arrays.asList("args0test1","args0test2"),new ArrayList<>());
            case 2:
                return copyPartialMatches(args[1],Arrays.asList("args1test1","args1test2"),new ArrayList<>());
            default:
                return new ArrayList<>();
        }
    }
}
