package me.btelnyy.nochatreport.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


import me.btelnyy.nochatreport.constants.Globals;

public class EventListener implements Listener {
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
