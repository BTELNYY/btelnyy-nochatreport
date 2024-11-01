package com.btelnyy.nochatreport.commands;

import com.btelnyy.nochatreport.NoChatReport;
import com.btelnyy.nochatreport.constants.ConfigData;
import com.btelnyy.nochatreport.constants.Globals;
import com.btelnyy.nochatreport.playerdata.DataHandler;
import com.btelnyy.nochatreport.playerdata.PlayerData;
import com.btelnyy.nochatreport.service.Utils;
import com.btelnyy.nochatreport.service.file_manager.Configuration;
import com.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandUnignore implements CommandExecutor, TabCompleter
{
    private static final String USAGE = "/unignore <player>";
    private static String invalidSyntax;
    Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();

    public CommandUnignore()
    {
        updateMessages();
    }

    public static void updateMessages()
    {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        invalidSyntax = Utils.coloured(language.getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(Utils.colored(language.getString("not_player")));
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
        if (player == target)
        {
            player.sendMessage(Utils.colored(language.getString("not_on_self")));
            return true;
        }
        if (target.isOp() || target.hasPermission(ConfigData.getInstance().noIgnorePermission))
        {
            player.sendMessage(Utils.colored(language.getString("cannot_ignore_staff")));
            return true;
        }
        PlayerData pdata = DataHandler.GetData(player);
        //verify that you even ignored that player in the first place
        if (pdata.ignoredUUIDs.contains(target.getUniqueId().toString()))
        {
            pdata.ignoredUUIDs.remove(target.getUniqueId().toString());
        } else
        {
            player.sendMessage(Utils.colored(language.getString("player_not_ignored")));
            return true;
        }
        //verify that the data is not null and does not cause more errors
        if (Globals.IgnoredPlayers.get(target.getUniqueId().toString()) == null)
        {
            Globals.IgnoredPlayers.put(target.getUniqueId().toString(), new ArrayList<String>());
        }
        // Is this a strange fix? yes, does it work? also yes
        try
        {
            Globals.IgnoredPlayers.get(target.getUniqueId().toString()).remove(player.getUniqueId().toString());
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        player.sendMessage(Utils.colored(language.getString("unignored_success").replace("{player_name}", target.getName())));
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
