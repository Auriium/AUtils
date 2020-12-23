package com.elytraforce.aUtils.core.command;

import java.util.List;

/**
 * Represents a simple command with handling for incorrect usage and arg counts.
 */
public abstract class ACommandExecutor implements ACommand {
    public abstract boolean isConsoleAccessible();

    public abstract void onIncorrectPermission(ASenderWrapper sender);
    public abstract void onIncorrectExecutor(ASenderWrapper sender);

    public abstract boolean onCommand(ASenderWrapper sender, String[] args);
    public abstract List<String> onTabComplete(ASenderWrapper sender, String[] args);


    @Override
    public boolean execute(ASenderWrapper sender, String[] args) {
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
    public List<String> executeTab(ASenderWrapper sender, String[] args) {
        return onTabComplete(sender,args);
    }


}
