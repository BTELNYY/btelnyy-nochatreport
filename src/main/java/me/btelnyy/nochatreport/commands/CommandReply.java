package me.btelnyy.nochatreport.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.constants.Globals;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;

public class CommandReply implements CommandExecutor{
    Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
    static final String USAGE = "/reply <message>";
    static String invalidSyntax;

    public CommandReply(){
        updateMessages();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendMessage(Utils.colored(language.getString("not_player")));
            return true;
        }
        if(args.length < 1){
            player.sendMessage(Utils.colored(invalidSyntax));
            return true;
        }
        if(!Globals.ReplyMap.containsKey(player)){
            player.sendMessage(language.getString("no_reply_target"));
            return true;
        }
        if(!Globals.ReplyMap.get(player).isOnline()){
            player.sendMessage(language.getString("reply_player_left"));
            return true;
        }
        Player target = Globals.ReplyMap.get(player);
        target.sendMessage(Utils.colored(formatMessage(language.getString("command_msg.target_message_format"), player, target, args)));
        player.sendMessage(Utils.colored(formatMessage(language.getString("command_msg.sender_message_format"), player, target, args)));
        return true;
    }

    static void updateMessages(){
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        invalidSyntax = Utils.coloured(language.getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }
    
    static String formatMessage(String str, Player sender, Player target, String[] args) {
        return str
                .replace("{target_name}", target.getDisplayName())
                .replace("{sender_name}", sender.getDisplayName())
                .replace("{message}", Utils.colored(Utils.buildMessage(args, true)));
    }
}
