package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.ActionHandler;

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

    public static class Booder {

        private final int position;

        private ActionHandler handler;
        private final String identifier;
        private LeafMap builderMap;

        public Booder(String id, int superpos, LeafMap map) {
            this.position = superpos + 1;
            this.builderMap = map;

            //todo Defaults below
            this.handler = new ActionHandler() {
                @Override
                public void run(ACommandSender sender, String[] args) {
                    sender.sendMessage("The developer of this plugin did not set up AuriumUtils correctly!");
                }
            };

            this.identifier = id;
        }

        public Booder setHandler(ActionHandler handler) {
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
