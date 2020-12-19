package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ACommandSender;

@FunctionalInterface
public interface ActionHandler {

    public boolean run(ACommandSender sender, String[] args);

}
