package com.elytraforce.aUtils.core.command.map;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.leaf.PointLeaf;
import com.elytraforce.aUtils.core.command.leaf.SplitLeaf;
import com.elytraforce.aUtils.core.command.leaf.ValueLeaf;
import com.elytraforce.aUtils.core.command.model.ActableLeaf;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.LeafConsumer;

import java.util.*;

/**
 * Represents a branching map of subcommands contained by a {@link com.elytraforce.aUtils.core.command.AMapExecutor}
 * Provides functional interfaces of {@link Leaf} via methods point, split, and value which provide {@link PointLeaf}, {@link SplitLeaf}
 * and {@link ValueLeaf} respectively. Can have a WrongArg Action. Automatically handles TabComplete.
 */
public class NewLeafMap {

    private final int min;
    private final int max;
    private final LinkedHashMap<Integer,LinkedHashSet<Leaf>> map;
    private final PointLeaf wrongArgsAction;
    private final Leaf baseLeaf;
    private final Boolean noArgs;

    public NewLeafMap(int min, int max, LinkedHashMap<Integer,LinkedHashSet<Leaf>> map, PointLeaf wrongArgsAction, Leaf baseLeaf, Boolean noArgs) {
        this.min = min;
        this.max = max;
        this.map = map;
        this.wrongArgsAction = wrongArgsAction;
        this.baseLeaf = baseLeaf;
        this.noArgs = noArgs;
    }

    //wrap method getPointingLeaf
    public boolean runPointingLeaf(ASenderWrapper wrapper, String[] args) {
        getPointingLeaf(args).getActionHandler(args).run(wrapper,args);
        return true;
    }

    public ActableLeaf getPointingLeaf(String[] args) {
        if (!noArgs) {
            if (args.length == 0 || args.length > max || args.length < min) {
                return wrongArgsAction;
            }
        }
        return baseLeaf.getPointingLeaf(args);
    }

    public List<String> getTabComplete(String[] args) {
        int currentPosition = args.length - 1;

        return baseLeaf.getTabSuggestions(currentPosition,args);
    }

    public static class Builder{

        private int builderPosition = -2;

        /*
        -2 represents the leafmap itself
        -1 represents the leafMap's baseCommand
        0 represents the first subcommand
        */

        private PointLeaf wrongArgsAction;
        private Leaf baseAction;
        private LinkedHashMap<Integer, LinkedHashSet<Leaf>> actions;

        private Integer min;
        private Integer max;
        private Boolean noArgs;

        private String commandName;

        public Builder(String commandName) {
            this.actions = new LinkedHashMap<>();
            this.baseAction = this.wrongArgsAction = new PointLeaf.Builder("errorBaseCommand",builderPosition,this).setHandler((sender,args) -> {
                sender.sendMessage("The developer of this plugin has not set up Leaf Framework correctly! Error: Missing BaseCommand!");
            }).createNoPut();
            this.noArgs = false;
            this.commandName = commandName;
        }

        public Builder withMin(int min) {
            this.min = min;
            return this;
        }

        public Builder withMax(int max) {
            this.max = max;
            return this;
        }

        public Builder hasNoArgs(boolean bool) {
            this.noArgs = bool;
            return this;
        }

        //there is no need for an id because the first is INSIDE of the command lol
        public Builder basePoint(LeafConsumer<PointLeaf.Builder,PointLeaf.Builder> builder) {
            baseAction = builder.accept(new PointLeaf.Builder("ignoredBasePoint",builderPosition,this)).create();
            return this;
        }

        public Builder baseSplit(LeafConsumer<SplitLeaf.Builder,SplitLeaf.Builder> builder) {
            baseAction = builder.accept(new SplitLeaf.Builder("ignoredBaseSplit",builderPosition,this)).create();
            return this;
        }

        public Builder baseValue(LeafConsumer<ValueLeaf.Builder,ValueLeaf.Builder> builder) {
            //using the create method is required as the baseaction's position of -1 is used to define the min arg of 0 by the builder
            baseAction = builder.accept(new ValueLeaf.Builder("ignoredBaseValue",builderPosition,this)).create();
            return this;
        }

        public NewLeafMap build() {
            //literally build!
            if (this.min == null) this.min = Collections.min(actions.keySet()) + 1;
            if (this.max == null) this.max = Collections.max(actions.keySet()) + 1;

            List<String> cum = new ArrayList<>();

            for (String string : baseAction.getDefaultUsage()) {
                cum.add("&b" + commandName + "&7 " + string);
            }

            if (this.wrongArgsAction == null) {
                this.wrongArgsAction = new PointLeaf.Builder("ignoredDefaultWrongArgs",builderPosition,this).setHandler((sender,args) -> {
                    sender.sendMessage("TODO: replace this with autobuilt");
                }).createNoPut();
            }

            return new NewLeafMap(min,max,actions,wrongArgsAction,baseAction,noArgs);
        }

        public void putInternal(Leaf leaf) {
            actions.computeIfAbsent(leaf.getPosition(), k -> new LinkedHashSet<>()).add(leaf);
        }
    }
}
