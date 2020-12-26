package com.elytraforce.aUtils.core.command.arguments;

import java.util.List;

//I have no clue how this is going to work
public class StringArrayArgument implements Argument<String[]>{

    private String identifier;
    private String[] defaultT;

    public StringArrayArgument(String identifier) {
        this.identifier = identifier;
        this.defaultT = of("testcum","test");
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public List<String> getBounds(int position, String positionString) {
        return List.of(identifier);
    }

    @Override
    public boolean check(String toParse) {
        return false;
    }

    @Override
    public String[] parse(String string) {
        return null;
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public String[] getDefault() {
        return null;
    }

    private String[] of (String... strings) {
        return strings;
    }
}
