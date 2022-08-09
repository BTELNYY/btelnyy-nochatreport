package me.btelnyy.nochatreport.commands;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.constants.ConfigData;
import me.btelnyy.nochatreport.constants.Globals;
import me.btelnyy.nochatreport.playerdata.DataHandler;
import me.btelnyy.nochatreport.playerdata.PlayerData;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;

public class CommandUnignore implements CommandExecutor, TabCompleter{
    Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
    private static final String USAGE = "/unignore <player>";
    private static String invalidSyntax;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg2, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage(Utils.colored(language.getString("not_player")));
            return true;
        }
        if(args.length < 1){
            sender.sendMessage(invalidSyntax);
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(player == target){
            player.sendMessage(Utils.colored(language.getString("not_on_self")));
            return true;
        }
        if(target.isOp() || target.hasPermission(ConfigData.getInstance().noIgnorePermission)){
            player.sendMessage(Utils.colored(language.getString("cannot_ignore_staff")));
            return true;
        }
        PlayerData pdata = DataHandler.GetData(player);
        pdata.ignoredUUIDs.remove(target.getUniqueId().toString());
        Globals.IgnoredPlayers.get(target.getUniqueId().toString()).remove(player.getUniqueId().toString());
        player.sendMessage(Utils.colored(language.getString("unignored_success").replace("{player_name}", target.getName())));
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String arg2, String[] args) {
        // Linked lists are faster when adding stuff
        List<String> list = new LinkedList<>();
        if (args.length != 1) {
            return list;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (commandSender.getName().equals(p.getName())) {
                continue;
            }
            list.add(p.getName());
        }
        return list;
    }

    public CommandUnignore(){
        updateMessages();
    }
    public static void updateMessages() {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        invalidSyntax = Utils.coloured(language.getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }
}
