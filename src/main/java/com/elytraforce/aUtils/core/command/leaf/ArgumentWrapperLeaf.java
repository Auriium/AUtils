package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.ActionHandler;

public class ArgumentWrapperLeaf implements ActablePointLeaf {

    private String identifier;
    private int position;

    private ValueableLeaf stored;
    private Argument argument;

    //TODO take an argument as a value
    public ArgumentWrapperLeaf(String identifier, ValueableLeaf leaf, Argument argument) {
        this.identifier = identifier;

        this.stored = leaf;
        this.argument = argument;
    }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        return stored.getActionHandler(args);
    }

    @Override
    public void register(int positionSuper, LeafMap map) {
        map.registerInternal(positionSuper,this);
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public void setPosition(int num) {
        this.position = num;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public ActablePointLeaf getPointingLeaf(String[] args) {
        return this;
    }
}
