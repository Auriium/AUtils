package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.command.AMapExecutor;
import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.map.NewLeafMap;

import java.util.ArrayList;
import java.util.List;

public class APointTest extends AMapExecutor {
    private NewLeafMap map = new NewLeafMap.Builder(getName()).basePoint(builder -> {
        return builder.setHandler((s,a) -> {
           s.sendMessage("CUM GOD");
        });
    }).hasNoArgs(true).build();

    @Override
    public NewLeafMap onCommandMap() {
        return map;
    }

    @Override
    public void onIncorrectPermission(ASenderWrapper sender) {
        sender.sendMessage("perms");
    }

    @Override
    public void onIncorrectExecutor(ASenderWrapper sender) {
        sender.sendMessage("executor");
    }

    @Override
    public String getName() {
        return "cum";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public String getPermission() {
        return "elytraforce.cool";
    }

    @Override
    public String getUsage() {
        return "balh";
    }

    @Override
    public String getDescription() {
        return "balh";
    }
}
