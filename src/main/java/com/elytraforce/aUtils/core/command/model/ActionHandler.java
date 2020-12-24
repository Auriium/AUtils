package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ASenderWrapper;

/**
 * A functional interface consumed by {@link Leaf}
 * allowing a command to interact directly with the sender and arguments sent.
 */
@FunctionalInterface
public interface ActionHandler {

    public void run(ASenderWrapper sender, String[] args);

}
