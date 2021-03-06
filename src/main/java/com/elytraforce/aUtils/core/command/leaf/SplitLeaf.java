package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.map.NewLeafMap;
import com.elytraforce.aUtils.core.command.model.ActableLeaf;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.LeafConsumer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a {@link Leaf} that provides passthrough to one or more leaves connected to it via point, split, and value methods
 * These methods are identical to that of {@link NewLeafMap}'s and register themselves onto the map unless otherwise specified.
 * This leaf is what allows complex subcommand routes.
 */
public class SplitLeaf implements Leaf {

    private final int position;
    private final String identifier;
    private final LinkedHashSet<Leaf> subset;
    private final ActableLeaf wrongArgsAction;

    private SplitLeaf(int position, String identifier, LinkedHashSet<Leaf> subset, ActableLeaf wrongArgsAction) {
        this.identifier = identifier;
        this.position = position;
        this.subset = subset;
        this.wrongArgsAction = wrongArgsAction;
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
    public ActableLeaf getPointingLeaf(String[] args) {
        if (args.length < position + 2) {
            return wrongArgsAction;
        }

        List<Leaf> leaflet = copyPartialMatches(args[position + 1],subset);

        if (leaflet.isEmpty()) { return wrongArgsAction; }
        return leaflet.get(0).getPointingLeaf(args);
    }

    @Override
    public List<String> getDefaultUsage() {
        return this.subset.stream()
                .flatMap(leaf -> leaf.getDefaultUsage().stream())
                .map(string -> string = this.identifier + "&b " + string).collect(Collectors.toList());
    }

    @Override
    public List<String> getTabSuggestions(int position, String[] stringArray) {
        int ahead = this.position + 1;
        List<Leaf> leaflet = copyPartialMatches(stringArray[ahead],subset);
        //if we are ONE AHEAD of the position this thing is at 2 <= 1
        if (position == ahead) {
            return leaflet.stream().map(Leaf::getIdentifier).collect(Collectors.toList());
        } else {
            if (leaflet.isEmpty()) {
                return new ArrayList<>();
            } else {
                return leaflet.get(0).getTabSuggestions(position,stringArray);
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

    public static class Builder {

        private final int position;

        private final String identifier;
        private final NewLeafMap.Builder builderMap;
        private final LinkedHashSet<Leaf> subset;
        private ActableLeaf wrongArgsAction;

        public Builder(String id, int superpos, NewLeafMap.Builder map) {
            this.position = superpos + 1;
            this.builderMap = map;
            this.identifier = id;
            this.subset = new LinkedHashSet<>();
            this.wrongArgsAction = new PointLeaf.Builder("ignored",position,map)
                    .setHandler((p,a) -> { p.sendMessage("The developer of this plugin did not set up AuriumUtils correctly!"); })
                    .create();
        }

        public SplitLeaf.Builder point(String id, LeafConsumer<PointLeaf.Builder,PointLeaf.Builder> builder) {
            Leaf leaf = builder.accept(new PointLeaf.Builder(id,position,builderMap)).create();

            this.subset.add(leaf);

            return this;
        }

        public SplitLeaf.Builder split(String id, LeafConsumer<Builder,SplitLeaf.Builder> builder) {
            Leaf leaf = builder.accept(new Builder(id,position,builderMap)).create();

            this.subset.add(leaf);

            return this;
        }

        public SplitLeaf.Builder value(String id, LeafConsumer<ValueLeaf.Builder,ValueLeaf.Builder> builder) {
            Leaf leaf = builder.accept(new ValueLeaf.Builder(id,position,builderMap)).create();

            this.subset.add(leaf);

            return this;
        }

        public SplitLeaf.Builder pointWrongArgs(LeafConsumer<PointLeaf.Builder,PointLeaf.Builder> builder) {
            wrongArgsAction = builder.accept(new PointLeaf.Builder("ignored",position,builderMap)).createNoPut();

            return this;
        }

        public SplitLeaf.Builder pointDefaultArgs(String id, LeafConsumer<PointLeaf.Builder,PointLeaf.Builder> builder) {
            PointLeaf leaf = builder.accept(new PointLeaf.Builder(id,position,builderMap)).create();

            wrongArgsAction = leaf;
            this.subset.add(leaf);

            return this;
        }

        public SplitLeaf create() {
            SplitLeaf leaf = new SplitLeaf(position,identifier,subset,wrongArgsAction);

            builderMap.putInternal(leaf);

            return leaf;
        }
    }


}
