package com.elytraforce.aExamples;

import com.elytraforce.aUtils.core.command.AMapExecutor;
import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.arguments.EnumArgument;
import com.elytraforce.aUtils.core.command.map.NewLeafMap;

import java.util.List;
import java.util.Set;

public class ACommandTest2 extends AMapExecutor {

    public enum Toggle {
        ON,OFF,DEFAULT
    }

    private Set<Enum> set = Set.of(Toggle.OFF,Toggle.ON);

    private NewLeafMap map = new NewLeafMap.Builder(getName())
            .hasNoArgs(true)
            .baseValue(builder -> {
                return builder
                        .argument(new EnumArgument("bool",set).withDefault(Toggle.DEFAULT))
                        .setHandler((sender,args) -> {
                            switch ((Toggle) args.getEnum("bool")) {
                                case ON:
                                    sender.sendMessage("ON");
                                    break;
                                case OFF:
                                    sender.sendMessage("OFF");
                                    break;
                                case DEFAULT:
                                    sender.sendMessage("DEFAULT");
                                    break;
                            }
                        })
                        .pointWrongArgs(builder1 -> builder1.setHandler((sender,args) -> {
                            sender.sendMessage(String.join(" ", args));
                        }));
            }).build();

    @Override
    public NewLeafMap onCommandMap() {
        return map;
    }

    @Override
    public void onIncorrectPermission(ASenderWrapper sender) {
        sender.sendMessage("bad permission");
    }

    @Override
    public void onIncorrectExecutor(ASenderWrapper sender) {
        sender.sendMessage("bad executor");
    }

    @Override
    public String getName() {
        return "cool";
    }

    @Override
    public List<String> getAliases() {
        return List.of("cheems");
    }

    @Override
    public String getPermission() {
        return "elytraforce.cool";
    }

    @Override
    public String getUsage() {
        return "do something cool";
    }

    @Override
    public String getDescription() {
        return "do something cool";
    }
}
