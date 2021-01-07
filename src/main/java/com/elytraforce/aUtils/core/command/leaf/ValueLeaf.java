package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.arguments.Arguments;
import com.elytraforce.aUtils.core.command.map.NewLeafMap;
import com.elytraforce.aUtils.core.command.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a {@link Leaf} that can have values in the form of {@link Argument}s
 * When built, runs provided {@link ArgumentHandler} to perform actions with arguments
 * Implements {@link ActableLeaf} as there should not be any leaves after this leaf on it's LeafMap path.
 */
public class ValueLeaf implements ActableLeaf {

    private final int position;
    private final String identifier;
    private final PointLeaf wrongArgsAction;
    private final LinkedHashMap<ArgumentWrapperLeaf,Integer> arguments;
    private final ArgumentHandler handler;

    private ValueLeaf(int position, String identifier, ArgumentHandler handler, LinkedHashMap<Argument<?>,Integer> map, PointLeaf wrongArgsAction, NewLeafMap.Builder builderMap) {
        this.identifier = identifier;
        this.position = position;
        this.wrongArgsAction = wrongArgsAction;
        this.arguments = new LinkedHashMap<>();

        map.forEach((arg,pos) -> {
            arguments.put(new ArgumentWrapperLeaf.Builder(arg.getIdentifier(),this.position + pos + 1,builderMap,arg,this).create(),pos);
        });

        this.handler = handler;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        int v = this.position + Collections.max(arguments.values()) + 1; //todo: check the maximum non optional arg
        if (args.length > v + 1) {
            //throw new IllegalStateException("Correct attion! lenght = " + args.length + " and v = " + v);
            return wrongArgsAction.getActionHandler(args);
        }

        Arguments holder = new Arguments();

        for (ArgumentWrapperLeaf argument : arguments.keySet()) {

            if(this.position + arguments.get(argument) + 1 >= args.length){
                //index not exists
                if (argument.getArgument().isOptional()) {
                    holder.add(argument.getIdentifier(),argument.getArgument().getDefault());
                } else {
                    return wrongArgsAction.getActionHandler(args);
                }
            }else{
                // index exists
                String positionString = args[this.position + arguments.get(argument) + 1];
                if (argument.getArgument().check(positionString)) {
                    return wrongArgsAction.getActionHandler(args);
                }
                holder.add(argument.getIdentifier(),argument.getArgument().parse(positionString));
            }

        }
        return getActionHandlerWithArguments(holder);
    }

    //TODO this needs to point to the correct argument-leaf
    @Override
    public ActableLeaf getPointingLeaf(String[] args) {
        return this;
    }

    @Override
    public List<String> getTabSuggestions(int position, String[] stringArray) {
        if (position <= Collections.max(this.arguments.values()) + this.position + 1) {
            return Objects.requireNonNull(getKeyByValue(arguments, position - this.position - 1)).getTabSuggestions(position, stringArray);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getDefaultUsage() {
        StringBuilder builder = new StringBuilder();

        builder.append(this.identifier);

        for (ArgumentWrapperLeaf arg : this.arguments.keySet()) {
            builder.append(" ").append("&7<&b").append(arg.getIdentifier()).append("&7>");
        }

        return List.of(builder.toString());

    }

    public ActionHandler getActionHandlerWithArguments(Arguments arguments) {
        return (sender, args) -> handler.run(sender,arguments);
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public static class Builder {

        private int nextArgumentPosition;
        private final int position;

        private final String identifier;
        private final NewLeafMap.Builder builderMap;
        private LinkedHashMap<Argument<?>,Integer> subset;
        private PointLeaf wrongArgsAction;

        private ArgumentHandler handler;

        public Builder(String id, int superpos, NewLeafMap.Builder map) {
            this.nextArgumentPosition = 0;
            this.position = superpos + 1;
            this.identifier = id;
            this.builderMap = map;
            this.wrongArgsAction = new PointLeaf.Builder("ignored",position,map)
                    .setHandler((p,a) -> { p.sendMessage("The developer of this plugin did not set up AuriumUtils correctly!"); })
                    .create();

            this.subset = new LinkedHashMap<>();

            this.handler = new ArgumentHandler() {
                @Override
                public void run(ASenderWrapper sender, Arguments args) {
                    sender.sendMessage("The developer of this plugin did not set up AuriumUtils correctly!");
                }
            };
        }

        public Builder pointWrongArgs(LeafConsumer<PointLeaf.Builder,PointLeaf.Builder> builder) {
            wrongArgsAction = builder.accept(new PointLeaf.Builder("ignored",position,builderMap)).createNoPut();

            return this;
        }

        public Builder argument(Argument<?> argument) {
            if (subset.containsValue(nextArgumentPosition)) {
                nextArgumentPosition++;
            }

            subset.put(argument,nextArgumentPosition);

            return this;
        }

        public Builder setHandler(ArgumentHandler handler) {
            this.handler = handler;
            return this;
        }

        public ValueLeaf create() {
            ValueLeaf leaf = new ValueLeaf(position,identifier,handler,subset,wrongArgsAction,builderMap);

            builderMap.putInternal(leaf);

            return leaf;
        }

        public ValueLeaf createNoPut() {
            return new ValueLeaf(position,identifier,handler,subset,wrongArgsAction,builderMap);
        }
    }

}
