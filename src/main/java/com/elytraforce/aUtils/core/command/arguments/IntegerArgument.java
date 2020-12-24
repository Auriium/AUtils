package com.elytraforce.aUtils.core.command.arguments;

import org.apache.commons.lang.math.NumberUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of {@link Argument} that provides an Integer.
 *
 * Can have bounds based on minimum and maximum numbers
 */
public class IntegerArgument implements Argument<Integer> {

    private String identifier;
    private Integer defaultT;

    private Integer min;
    private Integer max;

    private final Integer MAX_DISPLAYED_ENTRIES = 10;
    private final Integer NUMBER_SHIFT_MULTIPLIER = 10;

    public IntegerArgument(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    public IntegerArgument withMin(int min) {
        this.min = min;
        return this;
    }

    public IntegerArgument withMax(int max) {
        this.max = max;
        return this;
    }

    public IntegerArgument withDefault(int num) {
        this.defaultT = num;
        return this;
    }

    @Override
    public List<String> getBounds(int position, String positionString) {
        if (this.min == null || this.max == null) {
            return List.of(this.identifier);
        } else {
            Set<Long> numbers = new TreeSet<>();

            long inputNum = Long.parseLong(positionString.equals("-") ? "-0" : positionString.isEmpty() ? "0" : positionString);
            long inputNumAbsolute = Math.abs(inputNum);

            numbers.add(inputNumAbsolute);
            for (int i = 0; i < MAX_DISPLAYED_ENTRIES
                    && (inputNum * NUMBER_SHIFT_MULTIPLIER) + i <= max; i++) {
                numbers.add((inputNumAbsolute * NUMBER_SHIFT_MULTIPLIER) + i);
            }

            //rewrite cloud's solution with mine
            return numbers.stream().map(num -> {
                return positionString.startsWith("-") ? -num : num;
            }).filter(num -> {
                return !(num > max || num < min);
            }).map(String::valueOf).collect(Collectors.toList());
        }
    }

    @Override
    public boolean check(String type) {
        if (!NumberUtils.isNumber(type)) return true;
        if (max == null || min == null) {
            return false;
        } else {
            int num = Integer.parseInt(type);
            return (num > max || num < min);
        }
    }

    @Override
    public Integer parse(String string) {
        return Integer.parseInt(string);
    }

    @Override
    public boolean isOptional() {
        return this.defaultT != null;
    }
    @Override
    public Integer getDefault() {
        return this.defaultT;
    }


}
