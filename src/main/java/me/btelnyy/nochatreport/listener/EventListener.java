package me.btelnyy.nochatreport.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.btelnyy.nochatreport.constants.Globals;
import org.bukkit.ChatColor;

public class EventListener implements Listener {
    @EventHandler
    public void OnJoin(PlayerJoinEvent event){
        if(Globals.operatorAutoAddOnJoin && event.getPlayer().isOp()){
            Globals.AddPlayerToList(event.getPlayer());
            event.getPlayer().sendMessage(ChatColor.GRAY + "Your messages are automatically being spoofed, use /nochatreport to stop this.");
            return;
        }
        if(Globals.permissionAutoAddOnJoin && event.getPlayer().hasPermission(Globals.replaceMessagePermission)){
            Globals.AddPlayerToList(event.getPlayer());
            event.getPlayer().sendMessage(ChatColor.GRAY + "Your messages are automatically being spoofed, use /nochatreport to stop this.");
            return;
        }
    }
    @EventHandler
    public void OnPlayerChat(AsyncPlayerChatEvent event){
        if(Globals.sysMsgPlayers.contains(event.getPlayer())){
            ReplaceMessage(event);
            return;
        }
        if(event.getPlayer().isOp() && Globals.operatorsForcedToUse){
            ReplaceMessage(event);
            return;
        }
        if(event.getPlayer().hasPermission(Globals.replaceMessagePermission)){
            ReplaceMessage(event);
            return;
        }
        if(Globals.everyoneSysMessages){
            ReplaceMessage(event);
            return;
        }
    }
    static void ReplaceMessage(AsyncPlayerChatEvent event){
        //prevent duplication of messages
        event.setCancelled(true);
        String message = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
        for (Player p : event.getRecipients()) {
            
            p.sendMessage(message);
        }
        //feedback that the message was sent
        event.getPlayer().sendMessage(message);
    }
}
