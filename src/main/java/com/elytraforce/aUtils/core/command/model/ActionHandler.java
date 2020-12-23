package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ASenderWrapper;

@FunctionalInterface
public interface ActionHandler {

    public void run(ASenderWrapper sender, String[] args);

}
