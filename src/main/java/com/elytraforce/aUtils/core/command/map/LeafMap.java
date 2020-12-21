package com.elytraforce.aUtils.core.command.map;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.leaf.PointLeaf;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class LeafMap {

    private PointLeaf wrongArgsAction;
    private LinkedHashMap<Integer, LinkedHashSet<Leaf>> actions = new LinkedHashMap<>();

    public LeafMap put(Leaf leaf) {
        int position = -1; //-1 represents that this is positioned inside the command
        //e.g. position 0 representing argument 1, -1 represents noarg
        leaf.register(position,this);

        return this;
    }

    public LeafMap putWrongArgs(PointLeaf leaf) {
        this.wrongArgsAction = leaf;
        return this;
    }

    //the wrongargs is also added as an argument
    public LeafMap defaultWrongArgs(PointLeaf leaf) {
        this.wrongArgsAction = leaf;
        this.put(leaf);
        return this;
    }

    public boolean runActionFromArgs(ACommandSender sender, String[] args) {
        return getPointingLeaf(args).getActionHandler(args).run(sender, args);
    }

    public ActablePointLeaf getPointingLeaf(String[] args) {
        if (args.length == 0) {
            return wrongArgsAction;
        }

        ArrayList<Leaf> leaflet = copyPartialMatches(args[0],actions.get(0),new ArrayList<>());

        if (leaflet.isEmpty()) { return wrongArgsAction; }
        return leaflet.get(0).getPointingLeaf(args);
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

    public int registerInternal(int position, Leaf leaf) {
        int pos = position + 1;

        leaf.setPosition(pos);
        actions.computeIfAbsent(pos, k -> new LinkedHashSet<>()).add(leaf);

        return pos;
    }

}
