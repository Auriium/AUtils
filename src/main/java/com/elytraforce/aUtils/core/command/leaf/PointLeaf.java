package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;

public class PointLeaf implements ActablePointLeaf {

    private int position;
    private String identifier;

    private ActionHandler handler;

    public PointLeaf(String identifier, ActionHandler handler) {
        this.identifier = identifier;

        this.handler = handler;
    }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        return handler;
    }

    @Override
    public ActablePointLeaf getPointingLeaf(String[] args) {
        return this;
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

    //TODO: move this to the builder, make abstract builder abstract register. this would NOT
    //TODO: be build with stackstyle returning PointLeaf, but instead will return void and
    //TODO: only be called by the leafmap

    public void register(int positionSuper, LeafMap map) {
        map.registerInternal(positionSuper,this);
    }

    public static class Builder extends Leaf.Builder<PointLeaf> {



        public void register(int positionSuper, LeafMap map) {
            //map.registerInternal(positionSuper,this);
        }

        //public

        @Override
        public <T extends Leaf> T build() {
            return null;
        }
    }
}
