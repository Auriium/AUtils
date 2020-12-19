package com.elytraforce.aUtils.core.command.map;

import org.apache.commons.lang.Validate;

import java.util.*;
import java.util.function.Supplier;

//TODO: merge with leafmap in functionality
public class TabMap {
    private final HashMap<Integer, List<String>> result = new HashMap<>();

    public TabMap add(Integer position, List<String> string) {
        result.put(position,string);
        return this;
    }

    public TabMap add(Integer position, String... strings) {
        result.put(position, Arrays.asList(strings));
        return this;
    }

    public TabMap add(Integer position, Supplier<List<String>> consumer) {
        result.put(position, consumer.get());
        return this;
    }

    public List<String> getIntelligent(String[] args) {
        int arg = args.length - 1;

        return copyPartialMatches(args[arg],result.get(arg),new ArrayList<>());
    }

    private <T extends Collection<? super String>> T copyPartialMatches(final String token, final Iterable<String> originals, final T collection) throws UnsupportedOperationException, IllegalArgumentException {
        Validate.notNull(token, "Search token cannot be null");
        Validate.notNull(collection, "Collection cannot be null");
        Validate.notNull(originals, "Originals cannot be null");

        for (String string : originals) {
            if (startsWithIgnoreCase(string, token)) {
                collection.add(string);
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
