package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import org.apache.commons.lang.Validate;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SplitLeaf extends Leaf {

    private LinkedHashSet<Leaf> subset = new LinkedHashSet<>();
    private PointLeaf wrongArgsAction;

    public SplitLeaf(String identifier) {
        super(identifier);

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
    public ActionHandler getActionHandler(String[] args) {
        //position is THIS object's position, therefore the arg getter should represent what comes IN FRONT
        ArrayList<Leaf> leaflet = copyPartialMatches(args[position + 1],subset,new ArrayList<>());

        if (leaflet.isEmpty()) { return wrongArgsAction.getActionHandler(args); }
        return leaflet.get(0).getActionHandler(args);
    }

    public void cum(Consumer<String> cum) {
        cum.accept("bruh");
    }

    public void shit() {
        cum(cum -> {
            System.gc();//shit
        });
    }

    public void register(int positionSuper, LinkedHashMap<Integer, LinkedHashSet<Leaf>> map) {
        this.position = positionSuper + 1;

        map.computeIfAbsent(position, k -> new LinkedHashSet<>()).add(this);

        subset.forEach(leaf -> {
            leaf.register(position,map);
        });


        //we also need to register all furthers
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
