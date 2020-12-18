package com.elytraforce.aUtils.core.command;

import com.elytraforce.aUtils.core.command.model.ACommand;

import java.util.ArrayList;

public abstract class ACommandManager {

    protected ArrayList<ACommand> commands = new ArrayList<>();

    public abstract void registerCommand(ACommand command);
    public ArrayList<ACommand> getCommands() { return this.commands; };

}
