package com.elytraforce.aUtils.chat;

import com.elytraforce.aUtils.chat.models.DefaultFontInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class BChat {

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

    private final static char PLACEHOLDER = '\u02A7';

    public static TextComponent getMessageFromString( String message ) {
        return getMessageFromString( message, false );
    }

    public static TextComponent getMessageFromString( String message, boolean readExtra ) {
        TextComponent chatMessage = new TextComponent();

        if ( message == null ) {
            return chatMessage;
        }

        BaseComponent last = new TextComponent( "" );

        List< TextComponent > actions = new ArrayList< TextComponent >();
        // This is where we start looking for B+ formatting
        if ( readExtra ) {
            // Thanks to StarShadow#3546 for negative look behind
            // Will stop certain strings from getting converted
            List< String > caught = getMatches( message, "<(.+?)(?<!\\\\)>" );
            for ( String action : caught ) {
                String hover = getMatch( action, "\\{(.+?)(?<!\\\\)\\}" );
                String click = getMatch( action, "\\((.+?\\:.+?)(?<!\\\\)\\)" );
                if ( hover == null && click == null ) {
                    // Requires a click or hover, or skip
                    continue;
                }
                String desc = action;
                HoverEvent hAction = null;
                ClickEvent cAction = null;
                if ( click != null ) {
                    String[] clickSplit = click.split( "\\:", 2 );
                    ClickEvent.Action cActionAction;
                    try {
                        cActionAction = ClickEvent.Action.valueOf( clickSplit[ 0 ].toUpperCase() );
                    } catch ( IllegalArgumentException exception ) {
                        cActionAction = null;
                    }
                    if ( cActionAction == null ) {
                        if ( hover == null ) {
                            continue;
                        }
                    } else {
                        cAction = new ClickEvent( cActionAction, clickSplit[ 1 ] );
                        desc = desc.replace( "(" + click + ")", "" );
                    }
                }
                if ( hover != null ) {
                    hAction = new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( hover.replace( "\\n", "\n" ) ).create() );
                    desc = desc.replace( "{" + hover + "}", "" );
                }
                // Replace all the escaped tags
                desc = desc.replace( "\\{", "{" ).replace( "\\}", "}" );
                desc = desc.replace( "\\(", "(" ).replace( "\\)", ")" );
                TextComponent description = getMessageFromString( desc, false );
                for ( BaseComponent component : description.getExtra() ) {
                    component.setHoverEvent( hAction );
                    component.setClickEvent( cAction );
                }
                message = message.replace( "<" + action + ">", "" + ChatColor.COLOR_CHAR + PLACEHOLDER );
                actions.add( description );
            }
            // Replace all the escaped tags
            message = message.replace( "\\<", "<" ).replace( "\\>", ">" );
        }

        // More efficient conversion using split, as opposed to iterating over every character
        String[] parts = ( " " + message ).split( "" + ChatColor.COLOR_CHAR );
        for ( String part : parts ) {
            if ( part.isEmpty() ) {
                continue;
            }
            char colorCharacter = part.charAt( 0 );
            if ( colorCharacter == PLACEHOLDER ) {
                chatMessage.addExtra( actions.remove( 0 ) );
            } else {
                ChatColor color = ChatColor.getByChar( colorCharacter );

                if ( color != null ) {
                    if ( isColor( color ) ) {
                        clearFormatting( last );
                        last.setColor( color );
                    } else {
                        if ( color == ChatColor.BOLD ) {
                            last.setBold( true );
                        } else if ( color == ChatColor.ITALIC ) {
                            last.setItalic( true );
                        } else if ( color == ChatColor.UNDERLINE ) {
                            last.setUnderlined( true );
                        } else if ( color == ChatColor.MAGIC ) {
                            last.setObfuscated( true );
                        } else if ( color == ChatColor.RESET ) {
                            clearFormatting( last );
                        } else if ( color == ChatColor.STRIKETHROUGH ) {
                            last.setStrikethrough( true );
                        }
                    }
                }
            }
            if ( part.length() > 1 ) {
                if ( last instanceof TextComponent ) {
                    ( ( TextComponent ) last ).setText( part.substring( 1 ) );
                }
                chatMessage.addExtra( last );
                last = last.duplicate();
            }
        }

        return chatMessage;
    }

    private static void clearFormatting( BaseComponent component ) {
        component.setBold( false );
        component.setItalic( false );
        component.setUnderlined( false );
        component.setObfuscated( false );
        component.setStrikethrough( false );
    }

    private static boolean isColor( ChatColor color ) {
        return org.bukkit.ChatColor.valueOf( color.name() ).isColor();
    }

    private static List< String > getMatches( String string, String regex ) {
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( string );
        List< String > matches = new ArrayList< String >();
        while ( matcher.find() ) {
            matches.add( matcher.group( 1 ) );
        }
        return matches;
    }

    private static String getMatch( String string, String regex ) {
        Pattern pattern = Pattern.compile( regex );
        Matcher matcher = pattern.matcher( string );
        if ( matcher.find() ) {
            return matcher.group( 1 );
        } else {
            return null;
        }
    }

}