package me.btelnyy.nochatreport.commands;

import me.btelnyy.nochatreport.service.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMsg implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage(Utils.coloured("&cError: &7You must be a player to run this command."));
            return false;
        }
        if(args.length < 2){
            sender.sendMessage(Utils.coloured("&cError: &7Invalid syntax. Syntax: /msg <player> <message>"));
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            player.sendMessage(Utils.coloured("&cError: &7Player not found."));
            return false;
        }

        String message = Utils.buildMessage(args);
        target.sendMessage(Utils.coloured("&8&o" + player.getName() + " -> " + "You: " + message));
        player.sendMessage(Utils.coloured("&8&o" + "You:" + " -> " + target.getName() + ": " + message));
        return true;
    }
}
