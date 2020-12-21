package com.elytraforce.aUtils.core.command.arguments;

import java.util.Collection;

public abstract class Argument<T> {

    protected String identifier;
    protected int position;

    protected Argument(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
    public int getPosition() {
        return this.position;
    }

    public void setPosition(int num) {this.position = num; }

    //this always needs to return string, it's the tabcomplete thing
    public abstract Collection<String> getBounds(String[] args);

    public abstract boolean check(String toParse);
    public abstract T parse(String string);

}
