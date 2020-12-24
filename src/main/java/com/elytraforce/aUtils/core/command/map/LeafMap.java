package com.elytraforce.aUtils.core.command.map;

import com.elytraforce.aUtils.core.command.ASenderWrapper;
import com.elytraforce.aUtils.core.command.leaf.PointLeaf;
import com.elytraforce.aUtils.core.command.leaf.SplitLeaf;
import com.elytraforce.aUtils.core.command.leaf.ValueLeaf;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.LeafConsumer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a branching map of subcommands contained by a {@link com.elytraforce.aUtils.core.command.AMapExecutor}
 * Provides functional interfaces of {@link Leaf} via methods point, split, and value which provide {@link PointLeaf}, {@link SplitLeaf}
 * and {@link ValueLeaf} respectively. Can have a WrongArg Action. Automatically handles TabComplete.
 */
public class LeafMap {

    private final int position = -1;

    private PointLeaf wrongArgsAction;
    private final LinkedHashMap<Integer, LinkedHashSet<Leaf>> actions = new LinkedHashMap<>();

    public LeafMap point(String id, LeafConsumer<PointLeaf.Builder,PointLeaf> builder) {
        Leaf leaf = builder.accept(new PointLeaf.Builder(id,this.position,this));

        return this;
    }

    public LeafMap split(String id, LeafConsumer<SplitLeaf.Builder,SplitLeaf> builder) {
        Leaf leaf = builder.accept(new SplitLeaf.Builder(id,this.position,this));

        return this;
    }

    public LeafMap value(String id, LeafConsumer<ValueLeaf.Builder,ValueLeaf> builder) {
        Leaf leaf = builder.accept(new ValueLeaf.Builder(id,this.position,this));

        return this;
    }

    public LeafMap pointWrongArgs(LeafConsumer<PointLeaf.Builder,PointLeaf> builder) {

        wrongArgsAction = builder.accept(new PointLeaf.Builder("ignored",this.position,this));
        return this;

    }

    public void putInternal(Leaf leaf) {
        actions.computeIfAbsent(leaf.getPosition(), k -> new LinkedHashSet<>()).add(leaf);
    }

    public boolean runActionFromArgs(ASenderWrapper sender, String[] args) {
        getPointingLeaf(args).getActionHandler(args).run(sender, args);

        return true;
    }

    public ActablePointLeaf getPointingLeaf(String[] args) {
        if (args.length == 0) {
            return wrongArgsAction;
        }

        List<Leaf> leaflet = copyPartialMatches(args[0],actions.get(0));

        if (leaflet.isEmpty()) { return wrongArgsAction; }
        return leaflet.get(0).getPointingLeaf(args);
    }

    public List<String> getTabcomplete(ASenderWrapper sender, String[] args) {
        int currentPosition = args.length - 1;
        int ahead = this.position + 1;

        List<Leaf> leaflet = copyPartialMatches(args[ahead],actions.get(0));
        if (currentPosition == ahead) {
            return leaflet.stream().map(Leaf::getIdentifier).collect(Collectors.toList());
        } else {
            if (leaflet.isEmpty()) {
                return new ArrayList<>();
            } else {
                return leaflet.get(0).getBasedOnPosition(currentPosition,args);
            }
        }
    }

    private List<Leaf> copyPartialMatches(final String token, final Collection<Leaf> originals) throws UnsupportedOperationException, IllegalArgumentException {
        return originals.stream().filter(leaf -> startsWithIgnoreCase(leaf.getIdentifier(),token)).collect(Collectors.toList());
    }

    private boolean startsWithIgnoreCase(final String string, final String prefix) throws IllegalArgumentException, NullPointerException {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public int getMinArgs() {
        return Collections.max(actions.keySet()) + 1;
    }

    public int getMaxArgs() {
        return 0;
    }

}
