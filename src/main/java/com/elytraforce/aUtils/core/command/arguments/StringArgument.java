package com.elytraforce.aUtils.core.command.arguments;

import java.util.*;

public class StringArgument extends Argument<String> {

    private LinkedHashSet<String> limited;
    private String identifier;
    private int position;

    public StringArgument(String identifier) {
        super(identifier);
        this.limited = new LinkedHashSet<>();

    }

    public StringArgument withLimits(Collection<String> collection) {
        this.limited.addAll(collection);
        return this;
    }

    public StringArgument withLimits(String... strings) {
        this.limited.addAll(Arrays.asList(strings));
        return this;
    }

    @Override
    public Collection<String> getBounds(String[] args) {
        if (limited.isEmpty()) {
            return List.of(identifier);
        } else {
            return limited;
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
}
