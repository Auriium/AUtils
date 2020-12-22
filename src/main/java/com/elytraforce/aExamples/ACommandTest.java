package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.AMapExecutor;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.map.TabMap;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.Arrays;

public class ACommandTest extends AMapExecutor {

    @Inject private ConfigTest config;
    @Inject private ALogger logger;
    @Inject private AChat chat;

    private LeafMap map = new LeafMap()
            .point("arg0hello",builder -> {

                builder.setHandler((player,args) -> {
                    player.sendMessage("Hello, player!");
                    return true;
                });

                return builder.create();
            });



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
    public LeafMap onCommandMap() {
        return map;
    }
}
