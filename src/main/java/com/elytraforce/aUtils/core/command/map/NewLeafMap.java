package com.elytraforce.aUtils.core.command.map;

import com.elytraforce.aUtils.core.command.leaf.Leaf;
import com.elytraforce.aUtils.core.command.leaf.PointLeaf;
import com.elytraforce.aUtils.core.command.leaf.SplitLeaf;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.ToStringFunction;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;

import java.util.HashMap;

public class NewLeafMap {

    private boolean autoComplete;
    private PointLeaf wrongArgsAction;
    private HashMap<Integer, Leaf> actions;

    public NewLeafMap put(Leaf leaf) {
        actions.put(0,leaf);
        return this;
    }

    public NewLeafMap putBranching(SplitLeaf leaf) {
        actions.put(0,leaf);
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

}
