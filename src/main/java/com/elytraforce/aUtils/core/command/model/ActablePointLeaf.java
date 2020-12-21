package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.Leaf;

public interface ActablePointLeaf extends Leaf {

    public ActionHandler getActionHandler(String[] args);

}
