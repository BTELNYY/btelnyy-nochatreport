package me.btelnyy.nochatreport.commands;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.constants.ConfigData;
import me.btelnyy.nochatreport.constants.Globals;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandNCR implements CommandExecutor{
    private static final String USAGE = "/ncr|nochatreport <status|enable|disable|toggle>";
    private static String invalidSyntax;
    public CommandNCR(){
        updateMessages();
    }
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args){
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        if (!(sender instanceof Player player)){
            sender.sendMessage(Utils.coloured(language.getString("not_player")));
            return true;
        }
        if(args.length < 2){
            sender.sendMessage(invalidSyntax);
            return true;
        }
        UUID uuid = player.getUniqueId();
        switch(args[1]){
            default:
                sender.sendMessage(invalidSyntax);
                return true;
            case "status":
                if(NoChatReport.getInstance().getSystemMessagePlayers().contains(uuid)){
                    player.sendMessage(Utils.colored(language.getString("local_status_message").replace("{status}", language.getString("status_enabled"))));
                }else{
                    player.sendMessage(Utils.colored(language.getString("local_status_message").replace("{status}", language.getString("status_disabled"))));
                }
                if(!player.hasPermission(ConfigData.getInstance().showGlobalStatusPermission)){
                    return true;
                }
                if(Globals.pluginToggle){
                    player.sendMessage(Utils.colored(language.getString("global_status_message").replace("{status}", language.getString("status_enabled"))));
                }else{
                    player.sendMessage(Utils.colored(language.getString("global_status_message").replace("{status}", language.getString("status_disabled"))));
                }
                return true;
            case "toggle":
                if (NoChatReport.getInstance().getSystemMessagePlayers().contains(uuid)){
                    NoChatReport.getInstance().getSystemMessagePlayers().remove(uuid);
                    player.sendMessage(Utils.coloured(language.getString("removed_from_sys_list")));
                } else {
                    NoChatReport.getInstance().getSystemMessagePlayers().add(uuid);
                    player.sendMessage(Utils.coloured(language.getString("added_to_sys_list")));
                }
                return true;
            case "disable":
                if(!player.hasPermission(ConfigData.getInstance().changeGlobalStatusPermission)){
                    player.sendMessage(Utils.colored(language.getString("no_permission")));
                    return true;
                }
                Globals.pluginToggle = false;
                player.sendMessage(Utils.colored(language.getString("disabled_plugin")));
                return true;
            case "enable":
                if(!player.hasPermission(ConfigData.getInstance().changeGlobalStatusPermission)){
                    player.sendMessage(Utils.colored(language.getString("no_permission")));
                    return true;
                }
                Globals.pluginToggle = true;
                player.sendMessage(Utils.colored(language.getString("enabled_plugin")));
                return true;
        }
    }
    public static void updateMessages() {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        invalidSyntax = Utils.coloured(language.getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }
}
