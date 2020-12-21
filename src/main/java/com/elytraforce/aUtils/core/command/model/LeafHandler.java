package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.leaf.Leaf;

@FunctionalInterface
public interface LeafHandler {

    public void instance(Leaf accept);

}
