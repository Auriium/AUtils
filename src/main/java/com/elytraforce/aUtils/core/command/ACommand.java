package com.elytraforce.aUtils.core.command;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface representing something that can be executed and has certain values
 */
public interface ACommand {

    //this implements ACommand
    public String getName();
    public List<String> getAliases();
    public String getPermission();
    public String getUsage();
    public String getDescription();

    public boolean execute(ASenderWrapper sender, String[] args);
    public List<String> executeTab(ASenderWrapper sender, String[] args);

}
