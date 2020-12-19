package com.elytraforce.aUtils.core.chat;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface representing something that can interact with chat and chat colors
 */
public interface AChat {

    public String colorString(String string);

    public String colorString(Strings... strings);

    public ArrayList<String> colorStrings(String... strings);

    public ArrayList<String> colorStrings(List<String> strings);

    public String centerMessage(String string);

}
