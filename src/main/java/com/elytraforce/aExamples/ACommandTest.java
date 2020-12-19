package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.AMapExecutor;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.leaf.PointLeaf;
import com.elytraforce.aUtils.core.command.map.TabMap;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;

public class ACommandTest extends AMapExecutor {

    @Inject ConfigTest config;
    @Inject ALogger logger;

    private final LeafMap commandMap = new LeafMap.Builder()
            .setAutocomplete(true)
            .put(() -> {
                return PointLeaf.newInstance("cheat", (sender, args) -> {
                    sender.sendMessage(args[0] + " is bad!");
                    return true;
                });
            })
            .put(() -> {
                return PointLeaf.newInstance("supply", (sender, args) -> {
                    if (sender.hasPermission("jesus.penis")) {
                        sender.sendMessage("henlo");
                    } else {
                        sender.sendMessage("you are not jesus worshipper");
                    }
                    return true;
                });
            })
            .putWrongArgs(() -> {
                return PointLeaf.newInstance("supply", ((sender, args) -> {
                    sender.sendMessage("i'm COOMING (You fucked up somewhere)");
                    return true;
                }));
            })
            .build();


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
    public TabMap onTabMap(ACommandSender sender, String[] args) {
        return new TabMap()
                .add(0, () -> Arrays.asList("ss","vev"))
                .add(1,new ArrayList<>())
                .add(2, () -> Arrays.asList("dwd","ewe"))
                .add(3, () -> Arrays.asList("sad","things"))
                .add(4,"test","test2");

    }

    @Override
    public LeafMap onCommandMap(ACommandSender sender, String[] args) {
        return commandMap;
    }
}
