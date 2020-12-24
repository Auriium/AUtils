package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.ActionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * I have no idea why this class exists. It does not serve any purpose right now and therefore I have deprecated it
 * I would remove it except something tells me the cheese god will smite me if i do
 */
public class ArgumentWrapperLeaf implements ActablePointLeaf {

    private final ValueLeaf leaf;
    private final String identifier;
    private final int position;
    private final Argument argument;

    //TODO take an argument as a value
    public ArgumentWrapperLeaf(int position, String identifier, Argument argument, ValueLeaf leaf) {
        this.identifier = identifier;
        this.position = position;
        this.argument = argument;
        this.leaf = leaf;
    }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        return leaf.getActionHandler(args);
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public ActablePointLeaf getPointingLeaf(String[] args) {
        return this;
    }

    @Override
    public List<String> getBasedOnPosition(int currentPosition,String[] stringArray) {
        return new ArrayList<>();
    }

    public static class Builder {

        private final int position;

        private final Argument argument;
        private final String identifier;
        private final LeafMap builderMap;
        private final ValueLeaf leaf;

        public Builder(String id, int superpos, LeafMap map, Argument arg, ValueLeaf leaf) {
            this.position = superpos + 1;
            this.builderMap = map;
            this.identifier = id;
            this.leaf = leaf;
            this.argument = arg;
        }

        public ArgumentWrapperLeaf create() {
            ArgumentWrapperLeaf wrap = new ArgumentWrapperLeaf(position,identifier,argument,this.leaf);

            builderMap.putInternal(wrap);

            return wrap;
        }
    }
}
