package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.map.NewLeafMap;
import com.elytraforce.aUtils.core.command.model.ActionHandler;

import java.util.*;

public class PointLeaf extends Leaf {

    private ActionHandler handler;

    public PointLeaf(String identifier, ActionHandler handler) {
        super(identifier);
        this.handler = handler;
    }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        return handler;
    }

    //TODO: move this to the builder, make abstract builder abstract register. this would NOT
    //TODO: be build with stackstyle returning PointLeaf, but instead will return void and
    //TODO: only be called by the leafmap
    public void register(int positionSuper, LinkedHashMap<Integer, LinkedHashSet<Leaf>> map) {
        this.position = positionSuper + 1;

        map.computeIfAbsent(position, k -> new LinkedHashSet<>()).add(this);
    }
}
