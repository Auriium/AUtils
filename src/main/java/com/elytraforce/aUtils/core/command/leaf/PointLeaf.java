package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.LeafSupplier;

public class PointLeaf implements Leaf, LeafSupplier {

    private final String id;
    private final ActionHandler action;

    private PointLeaf(String id, ActionHandler action) {
        this.id = id;
        this.action = action;
    }

    public static PointLeaf newInstance(String id, ActionHandler action) {
        return new PointLeaf(id,action);
    }

    @Override
    public ActionHandler getHandle() {
        return action;
    }

    @Override
    public String getIdentifier() {
        return id;
    }

    @Override
    public Leaf get() {
        return this;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public boolean run(ACommandSender sender, String[] args) {
        return action.run(sender,args);
    }

    /*
    These calc arg methods are only used if someone is
    using this pointleaf as a supplier so it's assumed
    that the command is max 0 args (PointLeaf runs without taking args)

    If you want a command that only does one thing but takes arguments,
    use a SplitLeaf with some ValueLeafs.
     */

    @Override
    public Integer calcMinArgs() {
        return 0;
    }

    @Override
    public Integer calcMaxArgs() {
        return 0;
    }
}
