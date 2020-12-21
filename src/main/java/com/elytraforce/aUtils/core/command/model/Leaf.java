package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.map.LeafMap;

public interface Leaf {

    //TODO: move setpos into the builder, position should be final
    public String getIdentifier();
    public void setPosition(int num);
    public Integer getPosition();

    public ActablePointLeaf getPointingLeaf(String[] args);

    public void register(int positionSuper, LeafMap map);

    public abstract static class  Builder<LeafType extends Leaf> {

        protected int position;
        protected String identifier;

        public abstract <T extends Leaf> T build();

    }

}
