package me.btelnyy.nochatreport.commands;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandToggleMessages implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args){
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        if (!(sender instanceof Player player)){
            sender.sendMessage(Utils.coloured(language.getString("not_player")));
            return true;
        }
        UUID uuid = player.getUniqueId();
        if (NoChatReport.getInstance().getSystemMessagePlayers().contains(uuid)){
            NoChatReport.getInstance().getSystemMessagePlayers().remove(uuid);
            player.sendMessage(Utils.coloured(language.getString("removed_from_sys_list")));
        } else {
            NoChatReport.getInstance().getSystemMessagePlayers().add(uuid);
            player.sendMessage(Utils.coloured(language.getString("added_to_sys_list")));
        }
        return true;
    }
}
