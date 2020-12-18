package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.chat.AChat;
import com.elytraforce.aUtils.core.command.ACommandSender;
import com.google.inject.Inject;

public abstract class AFancyExecutor extends ACommandExecutor {

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

}
