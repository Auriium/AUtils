package com.elytraforce.aUtils.core.command.map;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.leaf.Leaf;
import com.elytraforce.aUtils.core.command.leaf.PointLeaf;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.ToStringFunction;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

/*public class LeafMap {

    private final PointLeaf errorAction;
    private final LinkedHashSet<Leaf> subCommandComponent;
    private final Boolean autoComplete;

    private LeafMap(PointLeaf wrongArgsAction, PointLeaf errorAction, LinkedHashSet<Leaf> subCommandComponent, Boolean autoComplete) {
        this.wrongArgsAction = wrongArgsAction;
        this.errorAction = errorAction;
        this.subCommandComponent = subCommandComponent;
        this.autoComplete = autoComplete;
    }

    public boolean run(ACommandSender sender, String[] args) {
        return getRelativeAction(args).getHandle().run(sender,args);
    }

    private Leaf getRelativeAction(String[] args) {
        if (args.length == 0) {
            return wrongArgsAction;
        }

        String first = args[0];

        if (autoComplete) {
            BoundExtractedResult<Leaf> result = FuzzySearch.extractOne(first, subCommandComponent, new ToStringFunction<Leaf>() {
                @Override
                public String apply(Leaf action) {
                    return action.getIdentifier();
                }
            });

            return result.getReferent();
        } else {
            HashMap<String, Leaf> map = new HashMap<>();
            for (Leaf commandComponent : subCommandComponent) {
                map.put(commandComponent.getIdentifier(), commandComponent);
            }
            for (String string : map.keySet()) {
                if (string.equalsIgnoreCase(first)) {
                    return map.get(string);
                }
            }
            return wrongArgsAction;
        }
    }

    public static class Builder {
        private PointLeaf wrongArgsAction;
        private PointLeaf errorAction;
        private LinkedHashSet<Leaf> subCommandComponent;
        private Boolean autoComplete;

        public Builder() {
            this.wrongArgsAction = PointLeaf.newInstance("ignored", (sender,args) -> {
                sender.sendMessage("Your arguments are incorrect! Reminder from Aurium - This message should be overwritten in the builder! If you see this message, contact an admin!");
                return true;
            });
            this.errorAction = PointLeaf.newInstance("ignored", (sender,args) -> {
                sender.sendMessage("An error occured while executing your command! Reminder from Aurium - This message should be overwritten in the builder! If you see this message, contact an admin!");
                return true;
            });
            this.autoComplete = false;
            this.subCommandComponent = new LinkedHashSet<>();
        }

        public Builder setAutocomplete(Boolean bool) {
            autoComplete = bool;
            return this;
        }

        public Builder put(Supplier<Leaf> action) {
            this.subCommandComponent.add(action.get());
            return this;
        }

        *//*This leaf is called when someone writes a wrong command with autocomplete off
        or when no arguments are entered. *//*
        public Builder putWrongArgs(Supplier<PointLeaf> action) {
            this.wrongArgsAction = action.get();
            return this;
        }


        *//*This leaf is called when an error occurs while running this leafMap TODO test this*//*
        public Builder putError(Supplier<PointLeaf> action) {
            this.errorAction = action.get();
            return this;
        }

        public LeafMap build() {
            return new LeafMap(wrongArgsAction,errorAction,subCommandComponent,autoComplete);
        }
    }

}*/
