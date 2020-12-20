package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.SplittableLeaf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

public class SplitLeaf implements Leaf, SplittableLeaf {

    private boolean autoComplete;
    private PointLeaf usageAction;
    private LinkedHashSet<Leaf> subCommandComponent;
    private String identifier;

    private ActionHandler handler;

    public SplitLeaf(String identifier) {
        this.identifier = identifier;
        this.subCommandComponent = new LinkedHashSet<>();

        handler = new ActionHandler() {
            @Override
            public boolean run(ACommandSender sender, String[] args) {
                return false;
            }
        };
    }

    @Override
    public ActionHandler getHandle() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Leaf get() {
        return this;
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public int getMaxArgsFromPosition(int supplied) {
        HashSet<Integer> values = new HashSet<>();

        if (this.subCommandComponent.isEmpty()) {
            values.add(supplied);
        } else {
            values.add(supplied + 1);
        }

        for (Leaf leaf : this.subCommandComponent) {
            if (leaf.isVariable() && leaf instanceof SplittableLeaf) {
                int val = ((SplittableLeaf) leaf).getMaxArgsFromPosition(supplied);
                values.add(val);
            }
        }
        return Collections.max(values);
    }

    @Override
    public PointLeaf getIncorrectUsageAction() {
        return usageAction;
    }

}
