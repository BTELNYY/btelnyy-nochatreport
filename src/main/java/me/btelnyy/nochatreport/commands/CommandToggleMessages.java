package me.btelnyy.nochatreport.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.btelnyy.nochatreport.constants.Globals;

import org.bukkit.command.Command;
import org.bukkit.ChatColor;

public class CommandToggleMessages implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Error: You must be a player to run this command.");
            return true;
        }
        Player p = (Player) sender;
        if(Globals.sysMsgPlayers.contains(p)){
            Globals.sysMsgPlayers.remove(p);
            p.sendMessage(ChatColor.GRAY + "Removed you from the system messages list. (Your messages will now be reportable!");
            return true;
        }else{
            Globals.sysMsgPlayers.add(p);
            p.sendMessage("Added you to the system messages list. (Your messages will now be spoofed to appear as if the server sent them!");
            return true;
        }
    }
}

