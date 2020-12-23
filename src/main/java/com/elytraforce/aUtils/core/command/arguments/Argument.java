package com.elytraforce.aUtils.core.command.arguments;

import java.util.Collection;
import java.util.List;

public interface Argument<T> {
    String getIdentifier();

    //this always needs to return string, it's the tabcomplete thing
    List<String> getBounds(int position, String positionString);

    boolean check(String toParse);
    T parse(String string);
    boolean isOptional();
    T getDefault();

}
