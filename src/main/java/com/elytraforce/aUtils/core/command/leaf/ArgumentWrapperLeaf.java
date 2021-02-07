package com.elytraforce.aUtils.core.command.leaf;

import com.elytraforce.aUtils.core.command.arguments.Argument;
import com.elytraforce.aUtils.core.command.map.NewLeafMap;
import com.elytraforce.aUtils.core.command.model.ActableLeaf;
import com.elytraforce.aUtils.core.command.model.ActionHandler;
import com.elytraforce.aUtils.core.command.model.Leaf;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//I KNEW THIS WOULD BE USEFUL
public class ArgumentWrapperLeaf implements ActableLeaf {

    private final ValueLeaf leaf;
    private final String identifier;
    private final int position;
    private final Argument<?> argument;

    //TODO take an argument as a value
    public ArgumentWrapperLeaf(int position, String identifier, Argument<?> argument, ValueLeaf leaf) {
        this.identifier = identifier;
        this.position = position;
        this.argument = argument;
        this.leaf = leaf;
    }

    public Argument<?> getArgument() { return this.argument; }

    @Override
    public ActionHandler getActionHandler(String[] args) {
        return leaf.getActionHandler(args);
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
        return leaf;
    }

    @Override
    public List<String> getTabSuggestions(int currentPosition, String[] stringArray) {
        String token = stringArray[currentPosition];
        return copyPartialMatches(token,argument.getBounds(currentPosition,token));
    }

    @Override
    public List<String> getDefaultUsage() {
        return null;
    }

    private List<String> copyPartialMatches(final String token, final Collection<String> originals) throws UnsupportedOperationException, IllegalArgumentException {
        return originals.stream().filter(arg -> startsWithIgnoreCase(arg,token)).collect(Collectors.toList());
    }

    private boolean startsWithIgnoreCase(final String string, final String prefix) throws IllegalArgumentException, NullPointerException {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static class Builder {

        private final int position;

        private final Argument<?> argument;
        private final String identifier;
        private final NewLeafMap.Builder builderMap;
        private final ValueLeaf leaf;

        public Builder(String id, int superpos, NewLeafMap.Builder map, Argument<?> arg, ValueLeaf leaf) {
            this.position = superpos + 1;
            this.builderMap = map;
            this.identifier = id;
            this.leaf = leaf;
            this.argument = arg;
        }

        public ArgumentWrapperLeaf create() {
            ArgumentWrapperLeaf wrap = new ArgumentWrapperLeaf(position,identifier,argument,this.leaf);

            builderMap.putInternal(wrap);

            return wrap;
        }
    }

}
