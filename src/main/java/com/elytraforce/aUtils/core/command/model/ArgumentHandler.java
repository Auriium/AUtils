package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.arguments.Arguments;

/**
 * A functional interface that works like an actionhandler except provides Arguments via interface. Used only by
 * {@link com.elytraforce.aUtils.core.command.leaf.ValueLeaf}
 */
@FunctionalInterface
public interface ArgumentHandler {

    public void run(ASenderWrapper sender, Arguments args);

}