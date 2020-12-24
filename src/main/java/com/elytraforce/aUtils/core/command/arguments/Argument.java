package com.elytraforce.aUtils.core.command.arguments;

import java.util.Collection;
import java.util.List;

/**
 * Represents an argument that provides values as well as default values and a list of all possible values.
 * @param <T> represents the type of value it can provide
 */
public interface Argument<T> {
    String getIdentifier();

    //this always needs to return string, it's the tabcomplete thing
    List<String> getBounds(int position, String positionString);

    boolean check(String toParse);
    T parse(String string);
    boolean isOptional();
    T getDefault();

}
