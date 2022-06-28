package me.btelnyy.nochatreport.commands;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class CommandMe implements CommandExecutor, TabCompleter {
    private static final String USAGE = "/me <action>";
    private static String invalidSyntax;

    public CommandMe() {
        updateMessages();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
        // If it is an instance of Player, it automatically assigns it to the "player" variable
        if(!(sender instanceof Player player)){
            sender.sendMessage(Utils.coloured(language.getString("not_player")));
            return true;
        }
        if (args.length > 1){
            player.sendMessage(invalidSyntax);
            return true;
        }
        Bukkit.broadcastMessage(Utils.coloured(
                language.getString("command_me.message_format")
                        .replace("{player_name}", player.getDisplayName())
                        .replace("{message}", Utils.buildMessage(args))
            )
        );
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

    public static void updateMessages() {
        invalidSyntax = Utils.coloured(NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration()
                .getString("invalid_syntax")
                .replace("{usage}", USAGE));
    }
}
