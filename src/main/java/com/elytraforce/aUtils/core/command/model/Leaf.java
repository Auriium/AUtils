package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.map.LeafMap;

import java.util.List;

public interface Leaf {

    //TODO: Maybe position needs to be defined in the constructor / constructor based leaf
    public String getIdentifier();
    public Integer getPosition();

    public ActablePointLeaf getPointingLeaf(String[] args);
    public List<String> getBasedOnPosition(int currentPosition,String[] stringArray);

    public interface Builder<LeafType extends Leaf> {

        public void setPosition(int num);

        //leaves are built on registering / putting / adding
        public LeafType build();
        public Leaf register(int positionSuper, LeafMap map);

    }

}
