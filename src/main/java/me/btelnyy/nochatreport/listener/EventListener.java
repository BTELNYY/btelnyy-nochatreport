package me.btelnyy.nochatreport.listener;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.constants.ConfigData;
import me.btelnyy.nochatreport.constants.Globals;
import me.btelnyy.nochatreport.playerdata.DataHandler;
import me.btelnyy.nochatreport.playerdata.PlayerData;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EventListener implements Listener
{
    private static final Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
    private static final String MESSAGES_SPOOFED = Utils.coloured(language.getString("message_spoofed"));
    private static final String MESSAGES_SPOOFED_FORCED = Utils.colored(language.getString("message_spoofed_forced"));

    static void replaceMessage(AsyncPlayerChatEvent event)
    {
        //Discord SRV fix
        if (ConfigData.getInstance().useAlternativeReplaceMethod)
        {
            //get string
            String message = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
            //people who should get the message
            Set<Player> pset = event.getRecipients();
            //if the
            if (Globals.IgnoredPlayers.get(event.getPlayer().getUniqueId().toString()) != null)
            {
                for (String p : Globals.CachedPlayers.get(event.getPlayer().getUniqueId().toString()).ignoredUUIDs)
                {
                    pset.remove(Bukkit.getPlayer(p));
                }
            }
            for (Player p : pset)
            {
                //try fix ignores not working
                if (Globals.CachedPlayers.get(event.getPlayer().getUniqueId().toString()).ignoredUUIDs.contains(p.getUniqueId().toString()))
                {
                    continue;
                }
                p.sendMessage(message);
            }
            event.getRecipients().clear();
            //Shouldn't be needed since the event is not cancelled, so the Vanilla logger
            //will pick it up normally.
            //NoChatReport.getInstance().getLogger().info("[CHAT] " + message);
            return;
        }
        // Cancel the event itself, so it there won't be duplicate messages
        event.setCancelled(true);

        // Send the message to all players who were supposed to get it
        String message = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
        if (Globals.IgnoredPlayers.get(event.getPlayer().getUniqueId().toString()) != null)
        {
            for (String p : Globals.CachedPlayers.get(event.getPlayer().getUniqueId().toString()).ignoredUUIDs)
            {
                event.getRecipients().remove(Bukkit.getPlayer(p));
            }
        }
        for (Player p : event.getRecipients())
        {
            p.sendMessage(message);
        }
        NoChatReport.getInstance().getLogger().info("[CHAT] " + message);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        if ((NoChatReport.getInstance().getConfigData().operatorAutoAddOnJoin &&
                        event.getPlayer().isOp()) || (NoChatReport.getInstance().getConfigData().permissionAutoAddOnJoin &&
                        event.getPlayer().hasPermission(NoChatReport.getInstance().getConfigData().replaceMessagePermission)))
        {
            NoChatReport.getInstance().getSystemMessagePlayers().add(event.getPlayer().getUniqueId());
            if (event.getPlayer().hasPermission("btelnyy.command.nochatreport"))
            {
                event.getPlayer().sendMessage(MESSAGES_SPOOFED);
            }
            else
            {
                event.getPlayer().sendMessage(MESSAGES_SPOOFED_FORCED);
            }
        }
        DataHandler.CreateNewDataFile(event.getPlayer());
        PlayerData pd = DataHandler.GetData(event.getPlayer()); //auto adds to the cached players Map in globals
        //For everyone who I ignore
        for (String UUID : pd.ignoredUUIDs)
        {
            //If they are not in the list of people being ignored server wide,
            //Add them and myself.
            if (!Globals.IgnoredPlayers.containsKey(UUID))
            {
                List<String> list = new ArrayList<String>();
                list.add(event.getPlayer().getUniqueId().toString());
                Globals.IgnoredPlayers.put(UUID, list);
            }
            //Add myself.
            else
            {
                Globals.IgnoredPlayers.get(UUID).add(event.getPlayer().getUniqueId().toString());
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerKickEvent event)
    {
        PlayerData pd = DataHandler.GetData(event.getPlayer()); //auto removes to the cached players Map in globals
        //For everyone I ignore
        for (String UUID : pd.ignoredUUIDs)
        {
            //Dont do anything if the person does not exist
            if (!Globals.IgnoredPlayers.containsKey(UUID))
            {
                return;
            }
            //Remove myself if the person does exist
            else
            {
                Globals.IgnoredPlayers.get(UUID).remove(event.getPlayer().getUniqueId().toString());
            }
        }
        Globals.ReplyMap.remove(event.getPlayer());
    }

    /*
        This line will make it so if another plugin cancels the message, the message won't be sent.
        Example: Muting
    */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        //no messages will be replaced if the toggle is active
        if (!Globals.pluginToggle)
        {
            return;
        }
        if (NoChatReport.getInstance().getSystemMessagePlayers().contains(event.getPlayer().getUniqueId()) ||
                (event.getPlayer().isOp() && NoChatReport.getInstance().getConfigData().operatorsForcedToUse) ||
                        event.getPlayer().hasPermission(NoChatReport.getInstance().getConfigData().replaceMessagePermission) ||
                        NoChatReport.getInstance().getConfigData().everyoneSysMessages)
        {
            replaceMessage(event);
        }
    }
}
