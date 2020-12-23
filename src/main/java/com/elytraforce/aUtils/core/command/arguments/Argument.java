package com.elytraforce.aUtils.core.command.arguments;

import java.util.Collection;
import java.util.List;

public abstract class Argument<T> {

    protected String identifier;
    protected int position;
    protected T defaultT;

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
    public abstract List<String> getBounds(int position, String positionString);

    public abstract boolean check(String toParse);
    public abstract T parse(String string);
    public abstract boolean isOptional();
    public abstract T getDefault();

}
