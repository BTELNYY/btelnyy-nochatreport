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


public class CommandMe implements CommandExecutor
{
    private static final String USAGE = "/me <action>";
    private static String invalidSyntax;

    public CommandMe()
    {
        updateMessages();
    }

    public static void updateMessages()
    {
        invalidSyntax = Utils.coloured(NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration()
                .getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args)
    {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        // If it is an instance of Player, it automatically assigns it to the "player" variable
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(Utils.coloured(language.getString("not_player")));
            return true;
        }
        if (args.length < 1)
        {
            player.sendMessage(invalidSyntax);
            return true;
        }
        Bukkit.broadcastMessage(Utils.coloured(
                        language.getString("command_me.message_format")
                                .replace("{player_name}", player.getDisplayName())
                                .replace("{message}", Utils.buildMessage(args, true))
                )
        );
        return true;
    }
}
