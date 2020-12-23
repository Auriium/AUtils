package com.elytraforce.aUtils.core.command.arguments;

import java.util.*;

public class BooleanArgument implements Argument<Boolean>{

    private String identifier;
    private Boolean defaultT;

    public BooleanArgument(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public BooleanArgument withDefault(Boolean bool) {
        this.defaultT = bool;
        return this;
    }

    @Override
    public List<String> getBounds(int position, String positionString) {
        return List.of("true","false");
    }

    @Override
    public boolean check(String type) {
        return !(type.equalsIgnoreCase("true")||type.equalsIgnoreCase("false"));
    }

    @Override
    public Boolean parse(String string) {
        return Boolean.valueOf(string);
    }

    @Override
    public boolean isOptional() {
        return this.defaultT != null;
    }

    @Override
    public Boolean getDefault() {
        return this.defaultT;
    }

}
