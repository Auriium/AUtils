package com.elytraforce.aUtils.core.command;

import com.elytraforce.aUtils.core.command.map.LeafMap;

import java.util.List;

/**
 * An AbstractCommandExecutor that consumes LeafMaps from Leaf Framework.
 * Extend this if you are trying to make a command with Leaf
 */
public abstract class AMapExecutor extends ACommandExecutor {

    @Override
    public boolean isConsoleAccessible() {
        return false;
    }

    @Override
    public List<String> onTabComplete(ASenderWrapper sender, String[] args) {
        return onCommandMap().getTabcomplete(sender, args);
    }

    @Override
    public boolean onCommand(ASenderWrapper sender, String[] args) {
        return onCommandMap().runActionFromArgs(sender, args);
    }

    public abstract LeafMap onCommandMap();

}
