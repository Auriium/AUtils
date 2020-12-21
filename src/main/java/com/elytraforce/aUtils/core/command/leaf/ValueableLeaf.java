package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.arguments.Arguments;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.ArgumentHandler;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.Leaf;

import java.util.LinkedHashMap;

public class ValueableLeaf implements ActablePointLeaf {

    private final int position;
    private final String identifier;
    private final PointLeaf wrongArgsAction;
    private final LinkedHashMap<Argument,Integer> arguments;
    private final ArgumentHandler handler;

    private ValueableLeaf(int position, String identifier, ArgumentHandler handler, LinkedHashMap<Argument,Integer> map, PointLeaf wrongArgsAction) {
        this.identifier = identifier;
        this.position = position;
        this.wrongArgsAction = wrongArgsAction;
        this.arguments = map;
        this.handler = handler;
    }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        Arguments holder = new Arguments();

        for (Argument argument : arguments.keySet()) {

            if(this.position + arguments.get(argument) + 1 >= args.length){
                //index not exists
                if (argument.isOptional()) {
                    holder.add(argument.getIdentifier(),argument.getDefault());
                } else {
                    return wrongArgsAction.getActionHandler(args);
                }
            }else{
                // index exists
                String positionString = args[this.position + arguments.get(argument) + 1];
                if (argument.check(positionString)) {
                    return wrongArgsAction.getActionHandler(args);
                }
                holder.add(argument.getIdentifier(),argument.parse(positionString));
            }

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
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public static class Builder implements Leaf.Builder<ValueableLeaf> {

        private int position;
        private final String identifier;
        private final LinkedHashMap<Argument,Integer> arguments;
        private PointLeaf.Builder wrongArgsAction;
        private final ArgumentHandler handler;

        private int nextArgumentPosition;


        private Builder(String id, ArgumentHandler handler) {
            this.identifier = id    ;
            this.handler = handler;
            this.position = 0;
            this.arguments = new LinkedHashMap<>();
            this.wrongArgsAction = PointLeaf.Builder.init("ignored",((sender, args) -> {
                sender.sendMessage("You have added the wrong arguments! // TODO: replace this");
                return true;
            })); //TODO: make the wrongArgsAction just relay autoWrongArgsBuilder

            this.nextArgumentPosition = 0;
        }

        public ValueableLeaf.Builder setWrongArgs(PointLeaf.Builder builder) {
            this.wrongArgsAction = builder;
            return this;
        }

        public ValueableLeaf.Builder argument(Argument argument) {
            if (arguments.containsValue(nextArgumentPosition)) {
                nextArgumentPosition++;
            }

            arguments.put(argument,nextArgumentPosition);
            return this;
        }

        @Override
        public Leaf register(int positionSuper, LeafMap map) {
            int pos = positionSuper + 1;
            Leaf returned = map.registerInternal(positionSuper,this);

            arguments.forEach((argument,num) -> map.registerInternal(pos + num + 1,ArgumentWrapperLeaf.Builder.init(argument.getIdentifier(),argument)));
            return returned;
        }

        @Override
        public void setPosition(int num) {
            this.position = num;
        }

        public static ValueableLeaf.Builder init(String id, ArgumentHandler handler) {
            return new ValueableLeaf.Builder(id,handler);
        }

        @Override
        public ValueableLeaf build() {
            return new ValueableLeaf(position,identifier,handler,arguments, wrongArgsAction.build());
        }
    }
}
