package me.btelnyy.nochatreport.commands;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.service.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandToggleMessages implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args){
        if (!(sender instanceof Player player)){
            sender.sendMessage(Utils.coloured("&cError: &7You must be a player to run this command."));
            return false;
        }
        if (NoChatReport.getInstance().getSystemMessagePlayers().contains(player)){
            NoChatReport.getInstance().getSystemMessagePlayers().remove(player);
            player.sendMessage(Utils.coloured("&c - &7Removed you from the system messages list. (Your messages will now be reportable!)"));
        } else {
            NoChatReport.getInstance().getSystemMessagePlayers().add(player);
            player.sendMessage(Utils.coloured("&a + &7Added you to the system messages list. (Your messages will now be spoofed to appear as if the server sent them!)"));
        }
        return true;
    }
}

