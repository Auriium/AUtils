package com.elytraforce.aUtils.core.command.map;


/*
public class LeafMap {

    private int basePosition = -2;

    private ActablePointLeaf wrongArgsAction;
    private final LinkedHashMap<Integer, LinkedHashSet<Leaf>> actions = new LinkedHashMap<>();

    private Integer maxArgs;
    private Integer minArgs;

    public LeafMap point(String id, LeafConsumer<PointLeaf.Builder,PointLeaf.Builder> builder) {
        Leaf leaf = builder.accept(new PointLeaf.Builder(id,basePosition + 1,this)).create();

        return this;
    }

    public LeafMap split(String id, LeafConsumer<SplitLeaf.Builder,SplitLeaf.Builder> builder) {
        Leaf leaf = builder.accept(new SplitLeaf.Builder(id,basePosition + 1,this)).create();

        return this;
    }

    public LeafMap value(String id, LeafConsumer<ValueLeaf.Builder,ValueLeaf.Builder> builder) {
        Leaf leaf = builder.accept(new ValueLeaf.Builder(id,basePosition + 1,this)).create();

        return this;
    }

    public LeafMap pointWrongArgs(LeafConsumer<PointLeaf.Builder,PointLeaf.Builder> builder) {

        //-1 just ensures that it will be taken without an identifier
        wrongArgsAction = builder.accept(new PointLeaf.Builder("ignored",basePosition,this)).createNoPut();
        return this;

    }

    public LeafMap pointDefaultArgs(String id, LeafConsumer<PointLeaf.Builder,PointLeaf.Builder> builder) {

        wrongArgsAction = builder.accept(new PointLeaf.Builder(id,basePosition + 1,this)).create();
        return this;

    }

    public LeafMap valueWrongArgs(LeafConsumer<ValueLeaf.Builder,ValueLeaf.Builder> builder) {

        //-1 just ensures that it will be taken without an identifier
        wrongArgsAction = builder.accept(new ValueLeaf.Builder("ignored",basePosition,this)).create();
        return this;

    }

    public LeafMap valueDefaultArgs(String id, LeafConsumer<ValueLeaf.Builder,ValueLeaf.Builder> builder) {

        wrongArgsAction = builder.accept(new ValueLeaf.Builder(id,basePosition + 1,this)).create();
        return this;

    }

    public LeafMap withMaxArgs(int amount) {
        this.maxArgs = amount;
        return this;
    }

    public LeafMap withMinArgs(int amount) {
        this.minArgs = amount;
        return this;
    }

    public void putInternal(Leaf leaf) {
        actions.computeIfAbsent(leaf.getPosition(), k -> new LinkedHashSet<>()).add(leaf);
    }

    public boolean runActionFromArgs(ASenderWrapper sender, String[] args) {
        if (this.maxArgs != null) {
            if (args.length > maxArgs || args.length < minArgs) {
                wrongArgsAction.getActionHandler(args).run(sender, args);
                return true;
            }
        }

        getPointingLeaf(args).getActionHandler(args).run(sender, args);

        return true;
    }

    public ActablePointLeaf getPointingLeaf(String[] args) {
        if (args.length == 0) {
            return wrongArgsAction;
        }

        List<Leaf> leaflet = copyPartialMatches(args[0],actions.get(Collections.min(actions.keySet())));

        if (leaflet.isEmpty()) { return wrongArgsAction; }
        return leaflet.get(0).getPointingLeaf(args);
    }

    public List<String> getTabcomplete(ASenderWrapper sender, String[] args) {
        int currentPosition = args.length - 1;

        List<Leaf> leaflet = copyPartialMatches(args[0],actions.get(Objects.requireNonNullElse(Collections.min(this.actions.keySet()),0)));
        if (currentPosition == 0) {
            return leaflet.stream().map(Leaf::getIdentifier).collect(Collectors.toList());
        } else {
            if (leaflet.isEmpty()) {
                return new ArrayList<>();
            } else {
                return leaflet.get(0).getTabSuggestions(currentPosition,args);
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

}
*/
