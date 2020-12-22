package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.LeafConsumer;
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

    public static class Booder {

        private final int position;

        private final String identifier;
        private final LeafMap builderMap;
        private final LinkedHashSet<Leaf> subset;
        private PointLeaf wrongArgsAction;

        public Booder(String id, int superpos, LeafMap map) {
            this.position = superpos + 1;
            this.builderMap = map;
            this.identifier = id;
            this.subset = new LinkedHashSet<>();
            this.wrongArgsAction = new PointLeaf.Booder("ignored",position,map)
                    .setHandler((p,a) -> { p.sendMessage("The developer of this plugin did not set up AuriumUtils correctly!"); })
                    .create();
        }

        public Booder point(String id, LeafConsumer<PointLeaf.Booder,PointLeaf> builder) {
            Leaf leaf = builder.accept(new PointLeaf.Booder(id,position,builderMap));

            this.subset.add(leaf);

            return this;
        }

        public Booder split(String id, LeafConsumer<SplitLeaf.Booder,SplitLeaf> builder) {
            Leaf leaf = builder.accept(new SplitLeaf.Booder(id,position,builderMap));

            this.subset.add(leaf);

            return this;
        }

        public Booder value(String id, LeafConsumer<ValueLeaf.Booder,ValueLeaf> builder) {
            Leaf leaf = builder.accept(new ValueLeaf.Booder(id,position,builderMap));

            this.subset.add(leaf);

            return this;
        }

        public Booder pointWrongArgs(LeafConsumer<PointLeaf.Booder,PointLeaf> builder) {
            wrongArgsAction = builder.accept(new PointLeaf.Booder("ignored",position,builderMap));

            return this;
        }

        public Booder pointDefaultArgs(String id, LeafConsumer<PointLeaf.Booder,PointLeaf> builder) {
            PointLeaf leaf = builder.accept(new PointLeaf.Booder(id,position,builderMap));

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
