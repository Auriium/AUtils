package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.leaf.Leaf;

public interface PermissibleLeaf extends Leaf {

    public boolean permission();

}
