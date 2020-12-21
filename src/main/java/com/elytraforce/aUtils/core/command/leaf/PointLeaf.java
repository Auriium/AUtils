package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;

public class PointLeaf implements ActablePointLeaf {

    private final int position;
    private final String identifier;

    private final ActionHandler handler;

    private PointLeaf(int position, String identifier, ActionHandler handler) {
        this.identifier = identifier;
        this.position = position;
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
    public Integer getPosition() {
        return position;
    }

    public static class Builder implements Leaf.Builder<PointLeaf> {

        private ActionHandler handler;
        private String identifier;
        private int position;

        private Builder(String id, ActionHandler handler) {
            this.identifier = id;
            this.handler = handler;
            this.position = 0;
        }

        @Override
        public Leaf register(int positionSuper, LeafMap map) {
            return map.registerInternal(positionSuper,this);
        }

        @Override
        public void setPosition(int num) {
            this.position = num;
        }

        public static PointLeaf.Builder init(String id, ActionHandler handle) {
            return new PointLeaf.Builder(id,handle);
        }

        @Override
        public PointLeaf build() {
            return new PointLeaf(position,identifier,handler);
        }
    }
}
