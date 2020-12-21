package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.model.ActionHandler;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public abstract class Leaf {

    protected Leaf(String identifier) {
        //where containerPosition represents the position number of the container holding leaf
        this.identifier = identifier;
        this.position = 0;

    }

    protected int position;
    protected String identifier;

    public String getIdentifier() { return this.identifier; }
    public Integer getPosition() { return this.position; }

    public abstract ActionHandler getActionHandler(String[] args);

    public abstract void register(int positionSuper, LinkedHashMap<Integer, LinkedHashSet<Leaf>> map);



}
