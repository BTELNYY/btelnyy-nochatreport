package me.btelnyy.nochatreport.commands;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.playerdata.DataHandler;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandIgnoreList implements CommandExecutor
{
    Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(Utils.colored(language.getString("not_player")));
            return true;
        }
        String result = "";
        for (String pid : DataHandler.GetData(player).ignoredUUIDs)
        {
            UUID id = UUID.fromString(pid);
            result += Bukkit.getOfflinePlayer(id).getName() + ", ";
        }
        player.sendMessage(Utils.colored(language.getString("ignorelist_result_message") + "(" + DataHandler.GetData(player).ignoredUUIDs.size() + ")" + "\n&7" + result));
        return true;
    }
}
