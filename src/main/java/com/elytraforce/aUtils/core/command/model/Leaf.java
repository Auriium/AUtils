package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.map.LeafMap;

import java.util.List;

/**
 * Represents a branching subcommand or command inside of a LeafMap
 * Has a position and an identifier where position is where on the
 * branching map it is located and identifier is the string that calls the subcommand
 */
public interface Leaf {

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
