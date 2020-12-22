package com.elytraforce.aUtils.core.command.map;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.leaf.PointLeaf;
import com.elytraforce.aUtils.core.command.leaf.SplitLeaf;
import com.elytraforce.aUtils.core.command.leaf.ValueLeaf;
import com.elytraforce.aUtils.core.command.model.ActablePointLeaf;
import com.elytraforce.aUtils.core.command.model.Leaf;
import com.elytraforce.aUtils.core.command.model.LeafConsumer;
import org.apache.commons.lang.Validate;

import java.util.*;

public class LeafMap {

    private final static int superPos = -1;

    private PointLeaf wrongArgsAction;
    private LinkedHashMap<Integer, LinkedHashSet<Leaf>> actions = new LinkedHashMap<>();

    public LeafMap point(String id, LeafConsumer<PointLeaf.Booder,PointLeaf> builder) {
        Leaf leaf = builder.accept(new PointLeaf.Booder(id,superPos,this));

        return this;
    }

    public LeafMap split(String id, LeafConsumer<SplitLeaf.Booder,SplitLeaf> builder) {
        Leaf leaf = builder.accept(new SplitLeaf.Booder(id,superPos,this));

        return this;
    }

    public LeafMap value(String id, LeafConsumer<ValueLeaf.Booder,ValueLeaf> builder) {
        Leaf leaf = builder.accept(new ValueLeaf.Booder(id,superPos,this));

        return this;
    }

    public LeafMap pointWrongArgs(LeafConsumer<PointLeaf.Booder,PointLeaf> builder) {

        wrongArgsAction = builder.accept(new PointLeaf.Booder("ignored",superPos,this));
        return this;

    }

    public void putInternal(Leaf leaf) {
        actions.computeIfAbsent(leaf.getPosition(), k -> new LinkedHashSet<>()).add(leaf);
    }



    /*public LeafMap put(Leaf.Builder<? extends Leaf> builder) {
        builder.register(-1,this);

        return this;
    }

    public Leaf registerInternal(int position, Leaf.Builder<? extends Leaf> builder) {
        int pos = position + 1;

        builder.setPosition(position);

        Leaf leaf = builder.build();


        return leaf;
    }*/

    /*public LeafMap putWrongArgs(PointLeaf.Builder leaf) {
        this.wrongArgsAction = leaf.build();
        return this;
    }

    //the wrongargs is also added as an argument
    public LeafMap defaultWrongArgs(PointLeaf.Builder leaf) {
        this.wrongArgsAction = leaf.build();
        this.put(leaf);
        return this;
    }*/

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

    public int getMinArgs() {
        return Collections.max(actions.keySet()) + 1;
    }

    public int getMaxArgs() {
        return 0;
    }

}
