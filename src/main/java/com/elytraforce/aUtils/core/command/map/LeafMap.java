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
import java.util.stream.Collectors;

public class LeafMap {

    private final static int superPos = -1;

    private PointLeaf wrongArgsAction;
    private final LinkedHashMap<Integer, LinkedHashSet<Leaf>> actions = new LinkedHashMap<>();

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

    public boolean runActionFromArgs(ACommandSender sender, String[] args) {
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

    public List<String> getTabcomplete(ACommandSender sender, String[] args) {
        int currentPosition = args.length - 1;
        String currentString = args[currentPosition];

        List<Leaf> leaflet = copyPartialMatches(args[0],actions.get(0));

        if (currentPosition <= 0) {
            return leaflet.stream().map(Leaf::getIdentifier).collect(Collectors.toList());
        } else {
            return leaflet.isEmpty() ? new ArrayList<>() : leaflet.get(0).getBasedOnPosition(currentPosition,currentString);
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
