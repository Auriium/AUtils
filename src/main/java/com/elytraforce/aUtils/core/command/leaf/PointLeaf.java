package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.ActionHandler;

import java.util.ArrayList;
import java.util.List;

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
    public List<String> getBasedOnPosition(int currentPosition, String[] stringArray) {
        return new ArrayList<>();
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public static class Builder {

        private final int position;

        private ActionHandler handler;
        private final String identifier;
        private LeafMap builderMap;

        public Builder(String id, int superpos, LeafMap map) {
            this.position = superpos + 1;
            this.builderMap = map;

            //todo Defaults below
            this.handler = new ActionHandler() {
                @Override
                public void run(ASenderWrapper sender, String[] args) {
                    sender.sendMessage("The developer of this plugin did not set up AuriumUtils correctly!");
                }
            };

            this.identifier = id;
        }

        public Builder setHandler(ActionHandler handler) {
            this.handler = handler;
            return this;
        }

        public PointLeaf create() {
            PointLeaf leaf = new PointLeaf(position,identifier,handler);

            builderMap.putInternal(leaf);

            return leaf;
        }

        public PointLeaf createNoPut() {
            return new PointLeaf(position,identifier,handler);
        }
    }

    //supplier needs to supply with a new builder passing position plus one to constructor, then save the builder and send it downrange

}
