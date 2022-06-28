package me.btelnyy.nochatreport.service;

import java.util.Arrays;

import org.bukkit.ChatColor;

public class Utils {
    /*
    Allows you to use colours in messages like
    "&cHello!"
    Which would be red
     */
    public static String coloured(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String colored(String str) {
        return coloured(str);
    }

    public static String buildMessage(String[] parts, boolean ignorefirst) {
        String message = "";
        if(ignorefirst){
            String[] yourArray = Arrays.copyOfRange(parts, 1, parts.length);
            for(String part : yourArray){
                message += part + " ";
            }
        }else{
            for (String part : parts) {
                message += part + " ";
            }
        }
        return message;
    }
}
