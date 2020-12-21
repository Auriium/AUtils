package com.elytraforce.aUtils.core.command;

import java.util.List;

/**
 * Represents a simple command with handling for incorrect usage and arg counts.
 */
public abstract class ACommandExecutor implements ACommand {
    public abstract boolean isConsoleAccessible();

    public abstract void onIncorrectPermission(ACommandSender sender);
    public abstract void onIncorrectExecutor(ACommandSender sender);

    public abstract boolean onCommand(ACommandSender sender, String[] args);
    public abstract List<String> onTabComplete(ACommandSender sender, String[] args);


    @Override
    public boolean execute(ACommandSender sender, String[] args) {
        if (sender.isConsole() && !isConsoleAccessible()) {
            onIncorrectExecutor(sender);
            return false;
        }

        if (!sender.hasPermission(getPermission())) {
            onIncorrectPermission(sender);
            return false;
        }

        return onCommand(sender,args);
    }

    @Override
    public List<String> executeTab(ACommandSender sender, String[] args) {
        return onTabComplete(sender,args);
    }


}
