package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.Leaf;

//function as placeholders for the tabcomplete
public class ArgumentWrapperLeaf implements ActablePointLeaf {

    private final String identifier;
    private final int position;
    private final Argument argument;

    //TODO take an argument as a value
    public ArgumentWrapperLeaf(int position, String identifier, Argument argument) {
        this.identifier = identifier;
        this.position = position;
        this.argument = argument;
    }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        return null;
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

    public static class Builder implements Leaf.Builder<ArgumentWrapperLeaf> {

        private final String identifier;
        private int position;
        private Argument argument;

        private Builder(String id, Argument argument) {
            this.identifier = id;
            this.argument = argument;
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

        public static ArgumentWrapperLeaf.Builder init(String id, Argument argument) {
            return new ArgumentWrapperLeaf.Builder(id,argument);
        }

        @Override
        public ArgumentWrapperLeaf build() {
            return new ArgumentWrapperLeaf(position,identifier,argument);
        }
    }
}
