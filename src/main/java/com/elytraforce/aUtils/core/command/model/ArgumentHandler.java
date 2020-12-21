package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.arguments.Arguments;

@FunctionalInterface
public interface ArgumentHandler {

    public boolean run(ACommandSender sender, Arguments args);

}