package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.map.LeafMap;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

public class SplitLeaf implements Leaf {

    private int position;
    private String identifier;

    private LinkedHashSet<Leaf> subset = new LinkedHashSet<>();
    private PointLeaf wrongArgsAction;

    public SplitLeaf(String identifier) {
        this.identifier = identifier;

        subset = new LinkedHashSet<>();
    }

    public SplitLeaf put(Leaf leaf) {
        this.subset.add(leaf);
        return this;
    }

    public SplitLeaf putWrongArgs(PointLeaf leaf) {
        this.wrongArgsAction = leaf;
        return this;
    }

    //the wrongargs is also added as an argument
    public SplitLeaf defaultWrongArgs(PointLeaf leaf) {
        this.wrongArgsAction = leaf;
        this.put(leaf);
        return this;
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

    public void register(int positionSuper, LeafMap map) {
        int pos = map.registerInternal(positionSuper,this);

        subset.forEach(leaf -> {
            leaf.register(pos,map);
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
