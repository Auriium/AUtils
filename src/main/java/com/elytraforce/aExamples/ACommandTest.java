package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.AMapExecutor;
import com.elytraforce.aUtils.core.command.arguments.StringArgument;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.logger.ALogger;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class ACommandTest extends AMapExecutor {

    @Inject private ConfigTest config;
    @Inject private ALogger logger;
    @Inject private AChat chat;

    @Override
    public boolean isConsoleAccessible() {
        return false;
    }

    @Override
    public String getName() {
        return "ballcommand";
    }

    @Override
    public List<String> getAliases() {
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
                });
            })
            .split("arg0split",builder -> {
                return builder
                        .pointDefaultArgs("arg1first",builder1 ->
                            builder1.setHandler((player, args) -> {
                                player.sendMessage("Ran command number 1!");
                            })
                        )
                        .point("arg1second",builder1 ->
                            builder1.setHandler((player,args) -> {
                                player.sendMessage("Ran command number 2!");
                            })
                        )
                        .value("arg1val", builder1 -> {
                            return builder1.argument(new StringArgument("cum_type"))
                                    .setHandler((sender, args) -> {
                                        sender.sendMessage("You have the nice color of " + args.getString("cum_type"));
                                    });
                        });
            })
            .split("arg0cum",builder -> {
                return builder
                        .pointDefaultArgs("arg1sex",builder1 -> {
                            return builder1.setHandler((player,args) -> {
                                player.sendMessage("Ran command number 1!");
                            });
                        })
                        .point("arg1flex",builder1 -> {
                            return builder1.setHandler((player,args) -> {
                                player.sendMessage("Ran command number 2!");
                            });
                        });
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
                            });
                        });
            })
            .pointWrongArgs(builder -> {
                return builder.setHandler((player,args) -> {
                    player.sendMessage(chat.colorString("&7&m---------&f<&bPlugin&9Test&f>&7&m---------"));
                    player.sendMessage(chat.colorString(""));
                    player.sendMessage(chat.colorString("&b/ballcommand &7arg0hello"));
                    player.sendMessage(chat.colorString("&b/ballcommand &7arg0split <arg1first/arg1second>"));
                });
            });

    @Override
    public LeafMap onCommandMap() {
        return map;
    }

    @Override
    public void onIncorrectPermission(ASenderWrapper sender) {
        return;
    }

    @Override
    public void onIncorrectExecutor(ASenderWrapper sender) {

    }
}
