package me.btelnyy.nochatreport.listener;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.constants.Globals;
import me.btelnyy.nochatreport.playerdata.PlayerData;
import me.btelnyy.nochatreport.playerdata.DataHandler;
import me.btelnyy.nochatreport.service.Utils;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

public class EventListener implements Listener {
    private static final Configuration language = NoChatReport.getInstance().getFileManager().getFile(FileID.LANGUAGE).getConfiguration();
    private static final String MESSAGES_SPOOFED = Utils.coloured(language.getString("message_spoofed"));
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if ((
                        NoChatReport.getInstance().getConfigData().operatorAutoAddOnJoin &&
                        event.getPlayer().isOp()
                ) || (
                        NoChatReport.getInstance().getConfigData().permissionAutoAddOnJoin &&
                        event.getPlayer().hasPermission(NoChatReport.getInstance().getConfigData().replaceMessagePermission)
                )
        ) {
            NoChatReport.getInstance().getSystemMessagePlayers().add(event.getPlayer().getUniqueId());
            event.getPlayer().sendMessage(MESSAGES_SPOOFED);
        }
        DataHandler.CreateNewDataFile(event.getPlayer());
        PlayerData pd = DataHandler.GetData(event.getPlayer()); //auto adds to the cached players Map in globals
        for(String UUID : pd.ignoredUUIDs){
            if(!Globals.IgnoredPlayers.containsKey(UUID)){
                List<String> list = new ArrayList<String>();
                list.add(event.getPlayer().getUniqueId().toString());
                Globals.IgnoredPlayers.put(UUID, list);
            }else{
                Globals.IgnoredPlayers.get(UUID).add(event.getPlayer().getUniqueId().toString());
            }
        }
    }
    @EventHandler
    public void onLeave(PlayerKickEvent event){
        PlayerData pd = DataHandler.GetData(event.getPlayer()); //auto adds to the cached players Map in globals
        for(String UUID : pd.ignoredUUIDs){
            if(!Globals.IgnoredPlayers.containsKey(UUID)){
                return;
            }else{
                Globals.IgnoredPlayers.get(UUID).remove(event.getPlayer().getUniqueId().toString());
            }
        }
    }

    /*
        This line will make it so if another plugin cancels the message, the message won't be sent.
        Example: Muting
        Something doesnt seem quite right... -btelnyy
    */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (
                NoChatReport.getInstance().getSystemMessagePlayers().contains(event.getPlayer().getUniqueId()) ||
                (event.getPlayer().isOp() && NoChatReport.getInstance().getConfigData().operatorsForcedToUse) ||
                event.getPlayer().hasPermission(NoChatReport.getInstance().getConfigData().replaceMessagePermission) ||
                NoChatReport.getInstance().getConfigData().everyoneSysMessages
        ) {
            replaceMessage(event);
        }
    }

    static void replaceMessage(AsyncPlayerChatEvent event){
        // Cancel the event itself, so it there won't be duplicate messages
        event.setCancelled(true);

        // Send the message to all players who were supposed to get it
        String message = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
        if(Globals.IgnoredPlayers.get(event.getPlayer().getUniqueId().toString()) != null){
            for(String p : Globals.IgnoredPlayers.get(event.getPlayer().getUniqueId().toString())){
                event.getRecipients().remove(Bukkit.getPlayer(p));
            }
        }
        for (Player p : event.getRecipients()) {
            p.sendMessage(message);
            NoChatReport.getInstance().getLogger().info("[CHAT] " + message);
        }
    }
}
