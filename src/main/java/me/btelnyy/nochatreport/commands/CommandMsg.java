package me.btelnyy.nochatreport.commands;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMsg implements CommandExecutor{
    private static final String USAGE = "/msg <player> <message>";
    private static String invalidSyntax;

    public CommandMsg() {
        updateMessages();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        if(!(sender instanceof Player player)){
            sender.sendMessage(Utils.coloured(language.getString("not_player")));
            return false;
        }
        if(args.length < 2){
            sender.sendMessage(invalidSyntax);
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null){
            player.sendMessage(Utils.coloured(language.getString("player_not_found")));
            return false;
        }

        target.sendMessage(formatMessage(language.getString("command_msg.target_message_format"), player, target, args));
        player.sendMessage(formatMessage(language.getString("command_msg.sender_message_format"), player, target, args));
        return true;
    }

    private static String formatMessage(String str, Player sender, Player target, String[] args) {
        return str
                .replace("{target_name}", target.getDisplayName())
                .replace("{sender_name}", sender.getDisplayName())
                .replace("{message}", Utils.buildMessage(args));
    }

    public static void updateMessages() {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        invalidSyntax = Utils.coloured(language.getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }
}
