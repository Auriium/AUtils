package com.elytraforce.aUtils.core.command.arguments;

import java.util.LinkedHashMap;

public class Arguments {

    //TODO: switch this with string, argument
    private LinkedHashMap<String,Object> container = new LinkedHashMap<>();

    public void add(String identifier, Object value) {
        this.container.put(identifier,value);
    }

    //TODO this must return an argument of the generic type
    //TODO make a getter for each argtype
    public String getString(String identifier) {
        return (String) container.get(identifier);
    }

    public boolean getBoolean(String identifier) {
        return (Boolean) container.get(identifier);
    }

    public int getInteger(String identifier) {
        return (int) container.get(identifier);
    }

    public Enum getEnum(String identifier) {
        return (Enum) container.get(identifier);
    }


}
