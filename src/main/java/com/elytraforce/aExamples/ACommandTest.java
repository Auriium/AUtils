package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.AMapExecutor;
import com.elytraforce.aUtils.core.command.arguments.StringArgument;
import com.elytraforce.aUtils.core.command.map.NewLeafMap;
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

    private NewLeafMap newMap = new NewLeafMap.Builder(getName())
            .baseSplit(builder -> {
                return builder
                        .point("args0hello",builder1 -> {
                            return builder1.setHandler((player,args) -> {
                                player.sendMessage("args0 command!");
                            });
                        })
                        .split("args0split",builder1 -> {
                            return builder1.pointDefaultArgs("first",builder2 -> {
                                return builder2.setHandler((sender,args) -> {
                                    sender.sendMessage("first command!");
                                });
                            }).point("second",builder2 -> {
                                return builder2.setHandler((sender,args) -> {
                                    sender.sendMessage("second command!");
                                });
                            }).value("split",builder2 -> {
                                return builder2.argument(new StringArgument("cum_type").withDefault("blue"))
                                        .setHandler((sender,args) -> {
                                            sender.sendMessage("You have the nice color of " + args.getString("cum_type"));
                                        });
                            });
                        })
                        .value("args0value",builder1 -> {
                            return builder1
                                    .argument(new StringArgument("string1"))
                                    .argument(new StringArgument("string2").withLimits("john","nipple").withDefault("john"))
                                    .setHandler((player,args) -> {
                                        player.sendMessage("You chose " + args.getString("string1") + "" + args.getString("string2"));
                                    })
                                    .pointWrongArgs(builder2 -> {
                                        return builder2.setHandler((player,arg) -> {
                                            player.sendMessage(chat.colorString("&cIncorrect Usage! &7Usage: /args0value <String> <john/nipple>"));
                                        });
                                    });
                        });
            }).build();

    @Override
    public NewLeafMap onCommandMap() {
        return newMap;
    }

    @Override
    public void onIncorrectPermission(ASenderWrapper sender) {
        return;
    }

    @Override
    public void onIncorrectExecutor(ASenderWrapper sender) {

    }
}
