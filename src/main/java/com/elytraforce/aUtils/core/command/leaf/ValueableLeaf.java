package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.arguments.Arguments;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.ArgumentHandler;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;

import java.util.Collections;
import java.util.LinkedHashMap;

public class ValueableLeaf implements ActablePointLeaf {

    private int position;
    private String identifier;

    private PointLeaf wrongArgsAction;
    private LinkedHashMap<Argument,Integer> arguments = new LinkedHashMap<>();
    private int nextArgumentPosition = 0; //todo move this to the builder;

    private ArgumentHandler handler;

    public ValueableLeaf(String identifier, ArgumentHandler handler) {
        this.identifier = identifier;

        this.handler = handler;
    }

    public ValueableLeaf putWrongArgs(PointLeaf leaf) {
        this.wrongArgsAction = leaf;
        return this;
    }

    public ValueableLeaf argument(Argument argument) {
        if (arguments.containsValue(nextArgumentPosition)) {
            nextArgumentPosition++;
        }

        arguments.put(argument,nextArgumentPosition);

        return this;
    }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        if (args.length < Collections.max(arguments.values()) + position + 1) return wrongArgsAction.getActionHandler(args);

        Arguments holder = new Arguments();

        for (Argument argument : arguments.keySet()) {
            String positionString = args[this.position + arguments.get(argument) + 1];

            if (argument.check(positionString)) {
                return wrongArgsAction.getActionHandler(args);
            }

            holder.add(argument.getIdentifier(),argument.parse(positionString));
            //TODO some kind of checker to make sure the argument is within a range
                    //Arguments#add(String identifier, String toInsert) <replace toInsert with Argument>
        }
        return getActionHandlerWithArguments(holder);
    }

    @Override
    public ActablePointLeaf getPointingLeaf(String[] args) {
        return this;
    }

    public ActionHandler getActionHandlerWithArguments(Arguments arguments) {
        //anonymous create new actionhandler anon
        return (sender, args) -> handler.run(sender,arguments);
    }

    @Override
    public void register(int positionSuper, LeafMap map) {
        int pos = map.registerInternal(positionSuper,this);

        arguments.forEach((argument,num) -> {
            map.registerInternal(pos + num + 1,new ArgumentWrapperLeaf(argument.getIdentifier(),this,argument));
        });
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public void setPosition(int num) {
        this.position = num;
    }

    @Override
    public Integer getPosition() {
        return position;
    }
}
