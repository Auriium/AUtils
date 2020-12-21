package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.AMapExecutor;
import com.elytraforce.aUtils.core.command.arguments.StringArgument;
import com.elytraforce.aUtils.core.command.leaf.SplitLeaf;
import com.elytraforce.aUtils.core.command.leaf.PointLeaf;
import com.elytraforce.aUtils.core.command.leaf.ValueableLeaf;
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
            .put(new PointLeaf("args0hello",(sender, args) -> {
                sender.sendMessage("i like dog");
                return true;
            }))
            .put(new SplitLeaf("args0split")
                    .put(new PointLeaf("args1cheese",(sender, args) -> {
                        sender.sendMessage("did you know humans are made of cow");
                        return true;
                    }))
                    .defaultWrongArgs(new PointLeaf("args1cow",(sender, args) -> {
                        sender.sendMessage("loud screaming noises from " + args[0]);
                        return true;
                    })))
            .put(new ValueableLeaf("args0value",(sender, args) -> {
                    sender.sendMessage(args.getString("string1"));
                    sender.sendMessage(args.getString("string2"));
                    return true;
                    })
                    .argument(new StringArgument("string1"))
                    .argument(new StringArgument("string2").withLimits("jake","jim"))
                    .putWrongArgs(new PointLeaf("ignored",(sender, args) -> {
                        sender.sendMessage("Usage: /args0value <string1> <jake/jim>");
                        return true;
                    }))
            )
            .putWrongArgs(new PointLeaf("ignored",(sender, args) -> {
                sender.sendMessage("ACommandTest - Commands");
                sender.sendMessage("/ballsack args0hello");
                sender.sendMessage("/ballsack args0split args1<cheese/cow>");
                sender.sendMessage("/ballsack args0value <string1> <jake/jim>");
                return true;
            }));

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public int getMaxArgs() {
        return 4;
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
    public LeafMap onCommandMap() {
        return map;
    }
}
