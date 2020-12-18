package com.elytraforce.aUtils.core.command.model;

import com.elytraforce.aUtils.core.command.ACommandSender;
import com.elytraforce.aUtils.core.command.model.ACommand;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class ACommandExecutor implements ACommand {

    public abstract int getMinArgs();
    public abstract int getMaxArgs();

    public abstract boolean isConsoleAccessible();

    public abstract void onIncorrectUsage(ACommandSender sender);
    public abstract void onIncorrectPermission(ACommandSender sender);
    public abstract void onIncorrectExecutor(ACommandSender sender);

    public abstract boolean onCommand(ACommandSender sender, String[] args);
    public abstract List<String> onTabComplete(ACommandSender sender, String[] args);


    @Override
    public boolean execute(ACommandSender sender, String[] args) {
        if (sender.isConsole() && !isConsoleAccessible()) {
            onIncorrectExecutor(sender);
            return false;
        }

        if (!sender.hasPermission(getPermission())) {
            onIncorrectPermission(sender);
            return false;
        }

        if (args.length > getMaxArgs() || args.length < getMinArgs()) {
            onIncorrectUsage(sender);
            return false;
        }

        return onCommand(sender,args);
    }

    @Override
    public List<String> executeTab(ACommandSender sender, String[] args) {
        return onTabComplete(sender,args);
    }

    public <T extends Collection<? super String>> T copyPartialMatches(final String token, final Iterable<String> originals, final T collection) throws UnsupportedOperationException, IllegalArgumentException {
        Validate.notNull(token, "Search token cannot be null");
        Validate.notNull(collection, "Collection cannot be null");
        Validate.notNull(originals, "Originals cannot be null");

        for (String string : originals) {
            if (startsWithIgnoreCase(string, token)) {
                collection.add(string);
            }
        }

        return collection;
    }

    public static boolean startsWithIgnoreCase(final String string, final String prefix) throws IllegalArgumentException, NullPointerException {
        Validate.notNull(string, "Cannot check a null string for a match");
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }
}
