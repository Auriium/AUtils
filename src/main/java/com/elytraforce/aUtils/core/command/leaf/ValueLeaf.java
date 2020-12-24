package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.arguments.Arguments;
import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a {@link Leaf} that can have values in the form of {@link Argument}s
 * When built, runs provided {@link ArgumentHandler} to perform actions with arguments
 * Implements {@link ActablePointLeaf} as there should not be any leaves after this leaf on it's LeafMap path.
 */
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

        for (Argument<?> argument : arguments.keySet()) {

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

    @Override
    public List<String> getBasedOnPosition(int position, String[] stringArray) {
        if (position <= Collections.max(this.arguments.values()) + this.position + 1) {
            List<String> leaflet = getSingleFromPosition(position - this.position - 1,stringArray[position]);
            return copyPartialMatches(stringArray[position],leaflet);
        } else {
            return new ArrayList<>();
        }
    }

    public ActionHandler getActionHandlerWithArguments(Arguments arguments) {
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

    private List<String> copyPartialMatches(final String token, final Collection<String> originals) throws UnsupportedOperationException, IllegalArgumentException {
        return originals.stream().filter(arg -> startsWithIgnoreCase(arg,token)).collect(Collectors.toList());
    }

    private boolean startsWithIgnoreCase(final String string, final String prefix) throws IllegalArgumentException, NullPointerException {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public List<String> getSingleFromPosition(int pos, String positionString) {
        Argument<?> arg = getKeyByValue(this.arguments,pos);

        Objects.requireNonNull(arg);
        return arg.getBounds(pos,positionString);
    }
    
    public static class Builder {

        private int nextArgumentPosition;
        private final int position;

        private final String identifier;
        private final LeafMap builderMap;
        private LinkedHashMap<Argument,Integer> subset;
        private PointLeaf wrongArgsAction;

        private ArgumentHandler handler;

        public Builder(String id, int superpos, LeafMap map) {
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

        public Builder argument(Argument argument) {
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
            ValueLeaf leaf = new ValueLeaf(position,identifier,handler,subset,wrongArgsAction);

            builderMap.putInternal(leaf);

            subset.forEach((arg,num) -> {
                new ArgumentWrapperLeaf.Builder(arg.getIdentifier(),position + num + 1,builderMap,arg,leaf).create();
            });

            return leaf;
        }

        public ValueLeaf createNoPut() {
            return new ValueLeaf(position,identifier,handler,subset,wrongArgsAction);
        }
    }

}
