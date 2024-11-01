package com.btelnyy.nochatreport.commands;

import com.btelnyy.nochatreport.NoChatReport;
import com.btelnyy.nochatreport.constants.Globals;
import com.btelnyy.nochatreport.service.Utils;
import com.btelnyy.nochatreport.service.file_manager.Configuration;
import com.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class CommandMsg implements CommandExecutor, TabCompleter
{
    private static final String USAGE = "/msg <player> <message>";
    private static String invalidSyntax;

    public CommandMsg()
    {
        updateMessages();
    }

    private static String formatMessage(String str, Player sender, Player target, String[] args)
    {
        return str
                .replace("{target_name}", target.getDisplayName())
                .replace("{sender_name}", sender.getDisplayName())
                .replace("{message}", Utils.colored(Utils.buildMessage(args, true)));
    }

    public static void updateMessages()
    {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        invalidSyntax = Utils.coloured(language.getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args)
    {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(Utils.coloured(language.getString("not_player")));
            return true;
        }
        if (args.length < 1)
        {
            sender.sendMessage(invalidSyntax);
            return true;
        }
        if (Bukkit.getPlayer(args[0]) == null)
        {
            player.sendMessage(Utils.colored(language.getString("player_not_found")));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            player.sendMessage(Utils.coloured(language.getString("player_not_found")));
            return true;
        }
        if (Globals.IgnoredPlayers.get(player.getUniqueId().toString()) != null)
        {
            if (Globals.IgnoredPlayers.get(player.getUniqueId().toString()).contains(target.getUniqueId().toString()))
            {
                player.sendMessage(Utils.colored(language.getString("cannot_message_player")));
                return true;
            }
        }
        target.sendMessage(Utils.colored(formatMessage(language.getString("command_msg.target_message_format"), player, target, args)));
        player.sendMessage(Utils.colored(formatMessage(language.getString("command_msg.sender_message_format"), player, target, args)));
        //add reply hashmap
        if (Globals.ReplyMap.containsKey(target))
        {
            Globals.ReplyMap.remove(target);
            Globals.ReplyMap.put(target, player);
        } else
        {
            Globals.ReplyMap.put(target, player);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String arg2, String[] args)
    {
        // Linked lists are faster when adding stuff
        List<String> list = new LinkedList<>();
        if (args.length != 1)
        {
            return list;
        }
        for (Player p : Bukkit.getOnlinePlayers())
        {
            if (commandSender.getName().equals(p.getName()))
            {
                continue;
            }
            list.add(p.getName());
        }
        return list;
    }
}
