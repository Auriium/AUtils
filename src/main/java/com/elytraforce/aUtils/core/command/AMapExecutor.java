package com.elytraforce.aUtils.core.command;

import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.command.map.NewLeafMap;
import com.elytraforce.aUtils.core.command.map.TabMap;
import com.google.inject.Inject;

import java.util.List;

public abstract class AMapExecutor extends ACommandExecutor {

    @Inject private AChat chat;

    public abstract String getPrefix();

    @Override
    public boolean isConsoleAccessible() {
        return false;
    }

    @Override
    public void onIncorrectUsage(ACommandSender sender) {
        sender.sendMessage(chat.colorString(getPrefix() + "&cIncorrect usage! Usage: &7" + getUsage()));
    }

    @Override
    public void onIncorrectPermission(ACommandSender sender) {
        sender.sendMessage(chat.colorString(getPrefix() + "&cIncorrect permissions!"));
    }

    @Override
    public void onIncorrectExecutor(ACommandSender sender) {
        sender.sendMessage(chat.colorString(getPrefix() + "&cYou cannot run this from the console!"));
    }

    @Override
    public List<String> onTabComplete(ACommandSender sender, String[] args) {
        sender.sendMessage("calling tabcomplete!");
        return onTabMap(sender,args).getIntelligent(args);
    }

    @Override
    public boolean onCommand(ACommandSender sender, String[] args) {
        return onCommandMap().runActionFromArgs(sender, args);
    }

    public abstract TabMap onTabMap(ACommandSender sender, String[] args);
    public abstract NewLeafMap onCommandMap();

}
