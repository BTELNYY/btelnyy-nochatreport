package me.btelnyy.nochatreport.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class CommandMe implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Error: You must be player to run this command.");
            return true;
        }
        Player player = (Player) sender;
        if(args.length > 1){
            player.sendMessage(ChatColor.RED + "Error: Invalid syntax. Syntax: /me <action>");
            return true;
        }
        String message = "";
        for(int i = 0; i < args.length; i++){
            message += (args[i] + " ");
        }
        Bukkit.broadcastMessage("* " + player.getName() + " " + message);
        return true;
    }
    
}
