package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.ActionHandler;

//function as placeholders for the tabcomplete
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

    public static class Booder {

        private final int position;

        private final Argument argument;
        private final String identifier;
        private final LeafMap builderMap;
        private final ValueLeaf leaf;

        public Booder(String id, int superpos, LeafMap map, Argument arg, ValueLeaf leaf) {
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
