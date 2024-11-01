package com.btelnyy.nochatreport.commands;

import com.btelnyy.nochatreport.NoChatReport;
import com.btelnyy.nochatreport.constants.Globals;
import com.btelnyy.nochatreport.service.Utils;
import com.btelnyy.nochatreport.service.file_manager.Configuration;
import com.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReply implements CommandExecutor
{
    static final String USAGE = "/reply <message>";
    static String invalidSyntax;
    Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();

    public CommandReply()
    {
        updateMessages();
    }

    static void updateMessages()
    {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        invalidSyntax = Utils.coloured(language.getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }

    static String formatMessage(String str, Player sender, Player target, String[] args)
    {
        return str
                .replace("{target_name}", target.getDisplayName())
                .replace("{sender_name}", sender.getDisplayName())
                .replace("{message}", Utils.colored(Utils.buildMessage(args, true)));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(Utils.colored(language.getString("not_player")));
            return true;
        }
        if (args.length < 1)
        {
            player.sendMessage(Utils.colored(invalidSyntax));
            return true;
        }
        if (!Globals.ReplyMap.containsKey(player))
        {
            player.sendMessage(Utils.colored(language.getString("no_reply_target")));
            return true;
        }
        if (!Globals.ReplyMap.get(player).isOnline())
        {
            player.sendMessage(Utils.colored(language.getString("reply_player_left")));
            return true;
        }
        Player target = Globals.ReplyMap.get(player);
        target.sendMessage(Utils.colored(formatMessage(language.getString("command_msg.target_message_format"), player, target, args)));
        player.sendMessage(Utils.colored(formatMessage(language.getString("command_msg.sender_message_format"), player, target, args)));
        return true;
    }
}
