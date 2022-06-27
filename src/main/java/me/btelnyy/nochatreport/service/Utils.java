package me.btelnyy.nochatreport.service;

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

    public static String buildMessage(String[] parts) {
        StringBuilder message = new StringBuilder();
        for (String part : parts) {
            message.append(part);
            message.append(" ");
        }
        return message.toString();
    }
}
