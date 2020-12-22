package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.AMapExecutor;
import com.elytraforce.aUtils.core.command.arguments.StringArgument;
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

    private LeafMap map = new LeafMap()
            .point("arg0hello",builder -> {
                return builder.setHandler((player,args) -> {
                    player.sendMessage("Hello, player!");
                }).create();
            })
            .split("arg0split",builder -> {
                return builder
                        .pointDefaultArgs("arg1first",builder1 -> {
                            return builder1.setHandler((player,args) -> {
                                player.sendMessage("Ran command number 1!");
                            }).create();
                        })
                        .point("arg1second",builder1 -> {
                            return builder1.setHandler((player,args) -> {
                                player.sendMessage("Ran command number 2!");
                            }).create();
                        }).create();
            })
            .value("args0value", builder -> {
                return builder
                        .argument(new StringArgument("string1"))
                        .argument(new StringArgument("string2").withLimits("john","nipple").withDefault("john"))
                        .setHandler((player,args) -> {
                            player.sendMessage("You chose " + args.getString("string1") + "" + args.getString("string2"));
                        })
                        .pointWrongArgs(builder1 -> {
                            return builder1.setHandler((player,arg) -> {
                                player.sendMessage(chat.colorString("&cIncorrect Usage! &7Usage: /args0value <String> <john/nipple>"));
                            }).createNoPut();
                        }).create();
            })
            .pointWrongArgs(builder -> {
                return builder.setHandler((player,args) -> {
                    player.sendMessage(chat.colorString("&7&m---------&f<&bPlugin&9Test&f>&7&m---------"));
                    player.sendMessage(chat.colorString(""));
                    player.sendMessage(chat.colorString("&b/ballcommand &7arg0hello"));
                    player.sendMessage(chat.colorString("&b/ballcommand &7arg0split <arg1first/arg1second>"));
                }).createNoPut();
            });

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
