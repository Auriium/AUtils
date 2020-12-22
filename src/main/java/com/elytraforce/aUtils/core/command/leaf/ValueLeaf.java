package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.arguments.Arguments;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.*;

import java.util.LinkedHashMap;

public class ValueLeaf implements ActablePointLeaf {

    private final int position;
    private final String identifier;
    private final PointLeaf wrongArgsAction;
    private final LinkedHashMap<Argument,Integer> arguments;
    private final ArgumentHandler handler;

    private ValueLeaf(int position, String identifier, ArgumentHandler handler, LinkedHashMap<Argument,Integer> map, PointLeaf wrongArgsAction) {
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

    //TODO this needs to point to the correct argument-leaf
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
    
    public static class Booder {

        private int nextArgumentPosition;
        private final int position;

        private final String identifier;
        private final LeafMap builderMap;
        private LinkedHashMap<Argument,Integer> subset;
        private PointLeaf wrongArgsAction;

        private ArgumentHandler handler;

        public Booder(String id, int superpos, LeafMap map) {
            this.nextArgumentPosition = 0;
            this.position = superpos + 1;
            this.identifier = id;
            this.builderMap = map;
            this.wrongArgsAction = new PointLeaf.Booder("ignored",position,map)
                    .setHandler((p,a) -> { p.sendMessage("The developer of this plugin did not set up AuriumUtils correctly!"); })
                    .create();

            this.subset = new LinkedHashMap<>();

            this.handler = new ArgumentHandler() {
                @Override
                public void run(ACommandSender sender, Arguments args) {
                    sender.sendMessage("The developer of this plugin did not set up AuriumUtils correctly!");
                }
            };
        }

        public Booder pointWrongArgs(LeafConsumer<PointLeaf.Booder,PointLeaf> builder) {
            wrongArgsAction = builder.accept(new PointLeaf.Booder("ignored",position,builderMap));

            return this;
        }

        public Booder argument(Argument argument) {
            if (subset.containsValue(nextArgumentPosition)) {
                nextArgumentPosition++;
            }

            subset.put(argument,nextArgumentPosition);

            return this;
        }

        public Booder setHandler(ArgumentHandler handler) {
            this.handler = handler;
            return this;
        }

        public ValueLeaf create() {
            ValueLeaf leaf = new ValueLeaf(position,identifier,handler,subset,wrongArgsAction);

            builderMap.putInternal(leaf);

            subset.forEach((arg,num) -> {
                new ArgumentWrapperLeaf.Booder(arg.getIdentifier(),position + num + 1,builderMap,arg,leaf).create();
            });

            return leaf;
        }
    }

}
