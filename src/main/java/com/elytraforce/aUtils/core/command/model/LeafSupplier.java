package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ACommandSender;

public interface LeafSupplier {

    public boolean run(ACommandSender sender, String[] args);

    public Integer calcMinArgs();
    public Integer calcMaxArgs();

}
