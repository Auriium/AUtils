package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.plugin.APlugin;

import java.util.ArrayList;
import java.util.List;

public interface ACommand {

    //this implements ACommand
    public String getName();
    public ArrayList<String> getAliases();
    public String getPermission();
    public String getUsage();
    public String getDescription();

    public boolean execute(ACommandSender sender, String[] args);
    public List<String> executeTab(ACommandSender sender, String[] args);

}
