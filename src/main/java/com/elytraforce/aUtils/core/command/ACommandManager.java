package com.elytraforce.aUtils.core.command;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public abstract class ACommandManager {

    protected LinkedHashSet<ACommand> commands = new LinkedHashSet<>();

    public abstract void registerCommand(ACommand command);
    public LinkedHashSet<ACommand> getCommands() { return this.commands; };

}
