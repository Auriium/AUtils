package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

public class SplitLeaf implements Leaf {

    private final int position;
    private final String identifier;
    private final LinkedHashSet<Leaf> subset;
    private final PointLeaf wrongArgsAction;

    private SplitLeaf(int position, String identifier, LinkedHashSet<Leaf> subset, PointLeaf wrongArgsAction) {
        this.identifier = identifier;
        this.position = position;
        this.subset = subset;
        this.wrongArgsAction = wrongArgsAction;
    }

    @Override
    public ActablePointLeaf getPointingLeaf(String[] args) {
        if (args.length < position + 2) {
            return wrongArgsAction;
        }

        ArrayList<Leaf> leaflet = copyPartialMatches(args[position + 1],subset,new ArrayList<>());

        if (leaflet.isEmpty()) { return wrongArgsAction; }
        return leaflet.get(0).getPointingLeaf(args);
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Integer getPosition() {
        return position;
    }

    public static class Builder implements Leaf.Builder<SplitLeaf> {

        private int position;
        private final String identifier;
        private final LinkedHashSet<Leaf.Builder<? extends Leaf>> subset;
        private PointLeaf.Builder wrongArgsAction;

        private final LinkedHashSet<Leaf> toPass;

        private Builder(String id) {
            this.identifier = id;
            this.position = 0;
            this.subset = new LinkedHashSet<>();
            this.wrongArgsAction = PointLeaf.Builder.init("ignored",((sender, args) -> {
                sender.sendMessage("You have added the wrong arguments! // TODO: replace this");

                return true;
            })); //TODO: make the wrongArgsAction just relay autoWrongArgsBuilder

            this.toPass = new LinkedHashSet<>();
        }

        public SplitLeaf.Builder put(Leaf.Builder<? extends Leaf> builder) {
            this.subset.add(builder);
            return this;
        }

        public SplitLeaf.Builder putWrongArgs(PointLeaf.Builder builder) {
            this.wrongArgsAction = builder;
            return this;
        }

        public SplitLeaf.Builder setWrongArgs(PointLeaf.Builder builder) {
            this.wrongArgsAction = builder;
            this.put(builder);
            return this;
        }

        @Override
        public Leaf register(int positionSuper, LeafMap map) {
            int pos = positionSuper + 1;
            Leaf returned = map.registerInternal(positionSuper,this);

            subset.forEach(builder -> {
                toPass.add(builder.register(pos,map));
            });

            return returned;
        }

        @Override
        public void setPosition(int num) {
            this.position = num;
        }

        public static SplitLeaf.Builder init(String id) {
            return new SplitLeaf.Builder(id);
        }

        @Override
        public SplitLeaf build() {
            return new SplitLeaf(position,identifier,toPass, wrongArgsAction.build());
        }
    }

    private <T extends Collection<? super Leaf>> T copyPartialMatches(final String token, final Collection<Leaf> originals, final T collection) throws UnsupportedOperationException, IllegalArgumentException {
        Validate.notNull(token, "Search token cannot be null");
        Validate.notNull(collection, "Collection cannot be null");
        Validate.notNull(originals, "Originals cannot be null");

        ;

        for (Leaf leaf : originals) {
            if (startsWithIgnoreCase(leaf.getIdentifier(), token)) {
                collection.add(leaf);
            }
        }

        return collection;
    }

    private boolean startsWithIgnoreCase(final String string, final String prefix) throws IllegalArgumentException, NullPointerException {
        Validate.notNull(string, "Cannot check a null string for a match");
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}
