package com.elytraforce.aUtils.core.command.arguments;

import com.google.common.collect.ImmutableList;

import java.util.*;

/**
 * An implementation of {@link Argument} that provides a String.
 *
 * Can have bounds based on a provided collection of Strings
 */
public class StringArgument implements Argument<String> {

    private String identifier;
    private String defaultT;
    private boolean caseSensitive;

    private LinkedHashSet<String> limited;

    public StringArgument(String identifier) {
        this.identifier = identifier;
        this.limited = new LinkedHashSet<>();
        this.caseSensitive = false;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public StringArgument withLimits(Collection<String> collection) {
        this.limited.addAll(collection);
        return this;
    }

    public StringArgument withLimits(String... strings) {
        this.limited.addAll(Arrays.asList(strings));
        return this;
    }

    public StringArgument withDefault(String string) {
        this.defaultT = string;
        return this;
    }

    @Override
    public List<String> getBounds(int position, String positionString) {
        if (limited.isEmpty()) {
            return List.of(this.identifier);
        } else {
            return new ArrayList<>(this.limited);
        }

    }

    @Override
    public boolean check(String type) {
        if (!limited.isEmpty()) {
            return !this.limited.contains(type);
        } else {
            return false;
        }
    }

    @Override
    public String parse(String string) {
        return string;
    }

    @Override
    public boolean isOptional() {
        return this.defaultT != null;
    }
    @Override
    public String getDefault() {
        return this.defaultT;
    }
}
