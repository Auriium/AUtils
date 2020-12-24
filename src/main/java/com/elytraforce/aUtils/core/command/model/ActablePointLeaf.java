package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.Leaf;

/**
 * Represents a {@link Leaf}
 * that has a directly related {@link ActionHandler} and that can perform actions given arguments.
 */
public interface ActablePointLeaf extends Leaf {

    public ActionHandler getActionHandler(String[] args);

}
