package me.btelnyy.nochatreport.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class CommandMsg implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Error: You must be a player to run this command.");
            return true;
        }
        Player player = (Player) sender;
        if(args.length < 2){
            sender.sendMessage(ChatColor.RED + "Error: Invalid syntax. Syntax: /msg <player> <message>");
            return true;
        }
        if(Bukkit.getPlayer(args[0]) == null){
            player.sendMessage(ChatColor.RED + "Error: Player not found.");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        //this really sucks, but what can you do.
        String message = "";
        for(int i = 1; i < args.length; i++){
            message += (args[i] + " ");
        }
        target.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + player.getName() + " -> " + "You: " + message);
        player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You:" + " -> " + target.getName() + ": " + message);
        return true;
    }
}
