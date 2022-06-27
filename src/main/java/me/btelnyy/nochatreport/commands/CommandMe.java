package me.btelnyy.nochatreport.commands;

import me.btelnyy.nochatreport.service.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMe implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        // If it is an instance of Player, it automatically assigns it to the "player" variable
        if(!(sender instanceof Player player)){
            sender.sendMessage(Utils.coloured("&cError: &7You must be a player to run this command."));
            return false;
        }
        if (args.length > 1){
            player.sendMessage(Utils.coloured("&cError: &7Invalid syntax. Syntax: /me <action>"));
            return false;
        }
        Bukkit.broadcastMessage("* " + player.getName() + " " + Utils.buildMessage(args));
        return true;
    }
    
}
