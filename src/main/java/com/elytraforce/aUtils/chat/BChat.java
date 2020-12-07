package com.elytraforce.aUtils.chat;

import com.elytraforce.aUtils.chat.models.AbstractChat;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class BChat extends AbstractChat {

    public static String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static List<String> colorString(List<String> strings) {
        ArrayList<String> stringList = new ArrayList<>();
        for (String string : strings) { stringList.add(colorString(string)); }

        return stringList;
    }



}