package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.leaf.PointLeaf;

public interface SplittableLeaf {

    public int getMaxArgsFromPosition(int supplied);

    public PointLeaf getIncorrectUsageAction();

}
