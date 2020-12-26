package com.elytraforce.aUtils.core.command.model;

import java.util.List;

/**
 * Represents a branching subcommand or command inside of a LeafMap
 * Has a position and an identifier where position is where on the
 * branching map it is located and identifier is the string that calls the subcommand
 */
public interface Leaf {

    public String getIdentifier();
    public Integer getPosition();

    //TODO let getPointingLeaf simply wrap getLinkedLeaf
    public ActableLeaf getPointingLeaf(String[] args);
    public List<String> getTabSuggestions(int currentPosition, String[] stringArray);
    public List<String> getDefaultUsage();

}
