package com.elytraforce.aUtils.chat;

import com.elytraforce.aUtils.chat.models.DefaultFontInfo;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AChat{

    public static String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static List<String> colorString(List<String> strings) {
        ArrayList<String> stringList = new ArrayList<>();
        for (String string : strings) { stringList.add(colorString(string)); }

        return stringList;
    }

    public static String centerMessage(String message) {
        if (message == null || message.equals("")) return "";
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
            if(c == '§'){
                previousCode = true;
            }else if(previousCode){
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            }else{
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int CENTER_PX = 154;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }

        return sb.toString() + message;
    }



}
