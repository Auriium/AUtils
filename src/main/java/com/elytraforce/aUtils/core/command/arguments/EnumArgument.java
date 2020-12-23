package com.elytraforce.aUtils.core.command.arguments;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumArgument implements Argument<Enum>{

    private String identifier;
    private Enum defaultT;

    private final Set<Enum> limited;

    public EnumArgument(String identifier, Collection<Enum> bounds) {
        this.identifier = identifier;
        this.limited = new LinkedHashSet<>(bounds);
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public EnumArgument withDefault(Enum num) {
        this.defaultT = num;
        return this;
    }

    @Override
    public List<String> getBounds(int position, String positionString) {
        return limited.stream().map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean check(String toParse) {
        for (Enum num : this.limited) {
            if (num.name().equalsIgnoreCase(toParse)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Enum parse(String string) {
        for (Enum num : this.limited) {
            if (num.name().equalsIgnoreCase(string)) {
                return num;
            }
        }
        return null;
    }

    @Override
    public boolean isOptional() {
        return this.defaultT != null;
    }

    @Override
    public Enum getDefault() {
        return this.defaultT;
    }
}
