package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.arguments.Arguments;

@FunctionalInterface
public interface ArgumentHandler {

    public void run(ASenderWrapper sender, Arguments args);

}